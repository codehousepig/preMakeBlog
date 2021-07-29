package codehouse.premakeblog.repository.search;

import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.entity.QChunu;
import codehouse.premakeblog.entity.QChunuImage;
import codehouse.premakeblog.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchChunuRepositoryImpl extends QuerydslRepositorySupport implements SearchChunuRepository{

    public SearchChunuRepositoryImpl() {
        super(Chunu.class);
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage............");

        QChunu qChunu = QChunu.chunu;
        QReply qReply = QReply.reply;
        QChunuImage qImage = QChunuImage.chunuImage; // 첨부 추가

        JPQLQuery<Chunu> jpqlQuery = from(qChunu);
        jpqlQuery.leftJoin(qImage).on(qImage.chunu.eq(qChunu)); // 첨부 추가
        jpqlQuery.leftJoin(qReply).on(qReply.chunu.eq(qChunu));

        JPQLQuery<Tuple> tuple =  jpqlQuery.select(qChunu, qImage, qReply.grade.avg().coalesce(0.0), qReply.count()); // 첨부 추가

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = qChunu.cno.gt(0L); // cno > 0 조건만 생성
        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");

            BooleanBuilder searchBuilder = new BooleanBuilder();
            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        searchBuilder.or(qChunu.title.contains(keyword));
                        break;
                    case "w":
                        searchBuilder.or(qChunu.writer.contains(keyword));
                        break;
                    case "c":
                        searchBuilder.or(qChunu.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(searchBuilder);
        }
        tuple.where(booleanBuilder);

        // order by
        Sort sort = pageable.getSort();

        // tuple.orderBy(board.bno.desc()); // 직접 코드로 처리하면
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Chunu.class, "chunu");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(qChunu);

        // page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        log.info(result);

        long count = tuple.fetchCount();
        log.info("Count: " + count);

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count
        );
    }

    @Override
    public Chunu search1() {
        log.info("search1.............");

        QChunu qChunu = QChunu.chunu;
        QReply qReply = QReply.reply;

        JPQLQuery<Chunu> jpqlQuery = from(qChunu);
        jpqlQuery.leftJoin(qReply).on(qReply.chunu.eq(qChunu));

        JPQLQuery<Tuple> tuple =  jpqlQuery.select(qChunu, qReply.count());
        tuple.groupBy(qChunu);

        log.info("-------------");
        log.info(tuple);
        log.info("-------------");

        List<Tuple> result = tuple.fetch();
        log.info(result);

        return null;
    }
}
