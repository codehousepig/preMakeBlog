package codehouse.premakeblog.repository;

import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("delete from Reply r " +
            "where r.chunu.cno = :cno")
    void deleteByCno(Long cno);

    // 게시물로 댓글 목록 가져오기
    List<Reply> getReplyByChunuOrderByRno(Chunu chunu);
}
