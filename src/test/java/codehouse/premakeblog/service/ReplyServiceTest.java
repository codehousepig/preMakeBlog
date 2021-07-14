package codehouse.premakeblog.service;

import codehouse.premakeblog.Service.ReplyService;
import codehouse.premakeblog.dto.ReplyDTO;
import codehouse.premakeblog.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Test
    public void getListTest2() {
        Long cno = 11L;
        List<ReplyDTO> result = replyService.getList(cno);
        result.forEach(i -> System.out.println(i));
    }
}
