package codehouse.premakeblog.Service;

import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.dto.PageRequestDTO;
import codehouse.premakeblog.dto.PageResultDTO;
import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.repository.ChunuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

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

    @Override
    public ChunuDTO read(Long cno) {
        Optional<Chunu> result = repository.findById(cno);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public PageResultDTO<ChunuDTO, Chunu> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("cno").descending());
        Page<Chunu> result = repository.findAll(pageable);
        Function<Chunu, ChunuDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }
}
