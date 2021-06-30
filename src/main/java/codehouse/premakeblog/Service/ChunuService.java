package codehouse.premakeblog.Service;

import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.entity.Chunu;

public interface ChunuService {
    Long register(ChunuDTO dto);

    default Chunu dtoToEntity(ChunuDTO dto) {
        Chunu entity = Chunu.builder()
                .cno(dto.getCno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }
}
