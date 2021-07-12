package codehouse.premakeblog.Service;

import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.dto.PageRequestDTO;
import codehouse.premakeblog.dto.PageResultDTO;
import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.entity.QChunu;
import codehouse.premakeblog.repository.ChunuRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    @Override
    public Long register(ChunuDTO dto) {
        log.info("input dto.............");
        log.info(dto);

        Chunu entity = dtoToEntity(dto);
        log.info(entity);

        repository.save(entity); // 테스트 후에 추가
        return entity.getCno(); // 테스트 후에 추가
    }

    @Override
    public void remove(Long cno) {
        repository.deleteById(cno);
    }

    @Override
    public void modify(ChunuDTO dto) {
        // 수정하는 내용은 제목, 내용
        Optional<Chunu> result = repository.findById(dto.getCno());
        if (result.isPresent()) {
            Chunu entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
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
        Optional<Chunu> result = repository.findById(cno);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public PageResultDTO<ChunuDTO, Chunu> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("cno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색 조건 처리
        Page<Chunu> result = repository.findAll(booleanBuilder, pageable); // Querydsl 사용
        Function<Chunu, ChunuDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }
}
