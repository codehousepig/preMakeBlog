package codehouse.premakeblog.repository;

import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

/*    @Transactional
    @Test
    public void readTest1() {
        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println("reply = " + reply);
        System.out.println(reply.getChunu());
    }*/

/*    @Test
    public void replyTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {

            // 1부터 30까지의 임의의 번호
            long cno = (long)(Math.random() * 30) + 1;

            Chunu chunu = Chunu.builder().cno(cno).build();

            Reply reply = Reply.builder()
                    .text("Reply..." + i)
                    .chunu(chunu)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }*/
}
