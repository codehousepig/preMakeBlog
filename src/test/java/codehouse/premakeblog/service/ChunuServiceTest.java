package codehouse.premakeblog.service;

import codehouse.premakeblog.Service.ChunuService;
import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.dto.PageRequestDTO;
import codehouse.premakeblog.dto.PageResultDTO;
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
    public void listTest(){
        PageRequestDTO requestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<ChunuDTO, Chunu> resultDTO = service.getList(requestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("-------------");
        for (ChunuDTO dto : resultDTO.getDtoList()) {
            System.out.println(dto);
        }
        System.out.println("=============");

        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

/*    @Test
    public void registerTest() {
        ChunuDTO dto = ChunuDTO.builder()
                .title("test_Title")
                .content("test_Contetnt")
                .writer("Chunu")
                .build();

        System.out.println(service.register(dto));
    }*/
}
