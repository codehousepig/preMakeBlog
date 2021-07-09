package codehouse.premakeblog.Service;

import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.dto.PageRequestDTO;
import codehouse.premakeblog.dto.PageResultDTO;
import codehouse.premakeblog.entity.Chunu;

public interface ChunuService {
    Long register(ChunuDTO dto);

    ChunuDTO read(Long cno);

    void remove(Long cno);

    void modify(ChunuDTO dto);

    PageResultDTO<ChunuDTO, Chunu> getList(PageRequestDTO requestDTO);

    default Chunu dtoToEntity(ChunuDTO dto) {
        Chunu entity = Chunu.builder()
                .cno(dto.getCno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }

    default ChunuDTO entityToDto(Chunu entity) {
        ChunuDTO dto = ChunuDTO.builder()
                .cno(entity.getCno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}
