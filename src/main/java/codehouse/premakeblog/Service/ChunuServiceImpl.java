package codehouse.premakeblog.Service;

import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.dto.PageRequestDTO;
import codehouse.premakeblog.dto.PageResultDTO;
import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.entity.ChunuImage;
import codehouse.premakeblog.entity.QChunu;
import codehouse.premakeblog.repository.ChunuImageRepository;
import codehouse.premakeblog.repository.ChunuRepository;
import codehouse.premakeblog.repository.ReplyRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 테스트 후에 추가
public class ChunuServiceImpl implements ChunuService{

    private final ChunuRepository repository; // 테스트 후에 추가
    private final ReplyRepository replyRepository;
    private final ChunuImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(ChunuDTO dto) {
        log.info("input dto: " + dto);

//        Chunu entity = dtoToEntity(dto);
//        repository.save(entity); // 테스트 후에 추가
        Map<String, Object> entityMap = dtoToEntity(dto);
        Chunu chunu = (Chunu) entityMap.get("chunu");
        List<ChunuImage> chunuImageList = (List<ChunuImage>) entityMap.get("imgList");

        repository.save(chunu);
        chunuImageList.forEach( img -> {
            imageRepository.save(img);
        });

        return chunu.getCno(); // 테스트 후에 추가
    }

    @Transactional // 트랜잭션 추가
    @Override
    public void remove(Long cno) {
        replyRepository.deleteByCno(cno); // 댓글 부터 삭제
        repository.deleteById(cno);
    }

    @Override
    public void modify(ChunuDTO dto) { // 수정하는 내용은 제목, 내용
    //        Optional<Chunu> result = repository.findById(dto.getCno());
        Chunu chunu = repository.getOne(dto.getCno());

        chunu.changeTitle(dto.getTitle());
        chunu.changeContent(dto.getContent());

        repository.save(chunu);
    }

    // 검색 처리 추가
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        // Querydsl 처리
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();
        QChunu qChunu = QChunu.chunu;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = qChunu.cno.gt(0L); // cno > 0 조건만 생성
        booleanBuilder.and(expression);
        if (type == null || type.trim().length() == 0) { // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        // 검색 조건 작성하기
        BooleanBuilder searchBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            searchBuilder.or(qChunu.title.contains(keyword));
        }
        if (type.contains("c")) {
            searchBuilder.or(qChunu.content.contains(keyword));
        }
        if (type.contains("w")) {
            searchBuilder.or(qChunu.writer.contains(keyword));
        }

        // 모든 조건 통합
        booleanBuilder.and(searchBuilder);
        return booleanBuilder;
    }

    @Override
    public ChunuDTO read(Long cno) {
        Object result = repository.getChunuByCno(cno);
        Object[] en = (Object[]) result;

        return entityToDTO((Chunu) en[0],
                (List<ChunuImage>)(Arrays.asList((ChunuImage)en[2])),
                (Double)en[3],
                (Long)en[4]);
    }

    @Override
    public PageResultDTO<ChunuDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info("CSImpl_PageResultDTO; " + pageRequestDTO);

        Page<Object[]> result = repository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("cno").descending())
        );

        System.out.println("result" + result);
        Function<Object[], ChunuDTO> fn = (en -> entityToDTO(
                (Chunu) en[0],
                (List<ChunuImage>)(Arrays.asList((ChunuImage)en[1])),
                (Double)en[2],
                (Long)en[3])
        );

        return new PageResultDTO<>(result, fn);
    }
}
