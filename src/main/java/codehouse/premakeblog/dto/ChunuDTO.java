package codehouse.premakeblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChunuDTO {

    private Long cno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate, modDate;
    private int replycount; // 해당 게시물의 댓글 수

    @Builder.Default
    private List<ChunuImageDTO> imageDTOList = new ArrayList<>();
}
