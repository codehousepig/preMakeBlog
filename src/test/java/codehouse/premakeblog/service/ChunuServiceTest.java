package codehouse.premakeblog.service;

import codehouse.premakeblog.Service.ChunuService;
import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.repository.ChunuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChunuServiceTest {

    @Autowired
    private ChunuService service;

    @Test
    public void registerTest() {
        ChunuDTO dto = ChunuDTO.builder()
                .title("test_Title")
                .content("test_Contetnt")
                .writer("Chunu")
                .build();

        System.out.println(service.register(dto));
    }
}
