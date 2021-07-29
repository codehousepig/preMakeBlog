package codehouse.premakeblog.Service;

import codehouse.premakeblog.dto.ChunuDTO;
import codehouse.premakeblog.dto.ChunuImageDTO;
import codehouse.premakeblog.dto.PageRequestDTO;
import codehouse.premakeblog.dto.PageResultDTO;
import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.entity.ChunuImage;

import java.text.Collator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ChunuService {
    Long register(ChunuDTO dto);

    ChunuDTO read(Long cno);

    void remove(Long cno);

    void modify(ChunuDTO dto);

    PageResultDTO<ChunuDTO, Object[]> getList(PageRequestDTO requestDTO);

/*    default Chunu dtoToEntity(ChunuDTO dto) {
        Chunu entity = Chunu.builder()
                .cno(dto.getCno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }*/
    default Map<String, Object> dtoToEntity(ChunuDTO chunuDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Chunu chunu = Chunu.builder()
                .cno(chunuDTO.getCno())
                .title(chunuDTO.getTitle())
                .content(chunuDTO.getContent())
                .writer(chunuDTO.getWriter())
                .build();
        entityMap.put("chunu", chunu);

        List<ChunuImageDTO> imageDTOList = chunuDTO.getImageDTOList();
        // ChunuImageDTO 처리
        if (imageDTOList != null && imageDTOList.size() > 0) {
            List<ChunuImage> chunuImageList = imageDTOList.stream().map(chunuImageDTO -> {
                ChunuImage chunuImage = ChunuImage.builder()
                        .folderPath(chunuImageDTO.getFolderPath())
                        .fileName(chunuImageDTO.getFileName())
                        .uuid(chunuImageDTO.getUuid())
                        .chunu(chunu)
                        .build();
                return chunuImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", chunuImageList);
        }

        return entityMap;
    }

/*    default ChunuDTO entityToDto(Chunu entity, Long replycount) {
        ChunuDTO dto = ChunuDTO.builder()
                .cno(entity.getCno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .replycount(replycount.intValue()) // long 으로 나오므로 int로 처리.
                .build();
        return dto;
    }*/
    default ChunuDTO entityToDTO(Chunu chunu, List<ChunuImage> chunuImages, Double avg, Long replyCount){
        ChunuDTO chunuDTO = ChunuDTO.builder()
                .cno(chunu.getCno())
                .title(chunu.getTitle())
                .content(chunu.getContent())
                .writer(chunu.getWriter())
                .regDate(chunu.getRegDate())
                .modDate(chunu.getModDate())
                .build();

        List<ChunuImageDTO> chunuImageDTOList = chunuImages.stream().map(img -> {
            if (img == null) {
                return null;
            } else {
                return ChunuImageDTO.builder()
                        .fileName(img.getFileName())
                        .folderPath(img.getFolderPath())
                        .uuid(img.getUuid())
                        .build();
            }
        }).collect(Collectors.toList());

        chunuDTO.setImageDTOList(chunuImageDTOList);
        chunuDTO.setAvg(avg);
        chunuDTO.setReplycount(replyCount.intValue());

        return chunuDTO;
    }

}
