package codehouse.premakeblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
