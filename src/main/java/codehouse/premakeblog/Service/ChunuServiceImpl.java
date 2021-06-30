package codehouse.premakeblog.Service;

import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.repository.ChunuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor // 테스트 후에 추가
public class ChunuServiceImpl implements ChunuService{

    private final ChunuRepository repository; // 테스트 후에 추가

    public Long register(ChunuDTO dto) {
        log.info("input dto.............");
        log.info(dto);

        Chunu entity = dtoToEntity(dto);
        log.info(entity);

        repository.save(entity); // 테스트 후에 추가
        return entity.getCno(); // 테스트 후에 추가
    }
}
