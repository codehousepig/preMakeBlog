package codehouse.premakeblog.repository;

import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.entity.QChunu;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ChunuRepositoryTests {

    @Autowired
    private ChunuRepository chunuRepository;

    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("cno").descending().and(Sort.by("title").ascending()));
        Page<Object[]> result = chunuRepository.searchPage("t", "1", pageable);
    }

/*    @Test
    public void search1Test() {
        chunuRepository.search1();
    }*/

/*    @Test
    public void query4Test() {
        Object result = chunuRepository.getChunuByCno(22L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }*/

/*    @Test
    public void query3Test() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cno").descending());
        Page<Object[]> result = chunuRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(i -> {
            Object[] arr = (Object[]) i;
            System.out.println(Arrays.toString(arr));
        });
    }*/

/*    @Test
    public void query2Test() {
        List<Object[]> result = chunuRepository.getChunuwithReply(20L);
        for (Object[] i : result) {
            System.out.println(Arrays.toString(i));
        }
    }*/

/*    @Test
    public void queryTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cno").descending());

        QChunu qChunu = QChunu.chunu; // 1
        String keyword = "1";
        BooleanBuilder booleanBuilder = new BooleanBuilder(); // 2
        BooleanExpression booleanExpression = qChunu.title.contains(keyword); // 3
        booleanBuilder.and(booleanExpression); // 4

        Page<Chunu> result = chunuRepository.findAll(booleanBuilder, pageable); // 5

        result.stream().forEach(i -> {
            System.out.println("i = " + i);
        });
    }*/

/*    @Test
    public void updateTest() {
        Optional<Chunu> result = chunuRepository.findById(30L);

        if (result.isPresent()) {
            Chunu posting = result.get();
            posting.changeTitle("Change Title...30");
            posting.changeContent("Change Content...30");
            chunuRepository.save(posting);
        }
    }*/

/*    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Chunu posting = Chunu.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("Chunu")
                    .build();
            chunuRepository.save(posting);
        });
    }*/
}
