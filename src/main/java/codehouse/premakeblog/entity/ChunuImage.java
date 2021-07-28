package codehouse.premakeblog.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "chunu")
public class ChunuImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String fileName;

    private String folderPath;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chunu chunu;
}
