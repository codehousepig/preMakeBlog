package codehouse.premakeblog.repository;

import codehouse.premakeblog.entity.Chunu;
import codehouse.premakeblog.repository.search.SearchChunuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChunuRepository extends JpaRepository<Chunu, Long>, QuerydslPredicateExecutor<Chunu>, SearchChunuRepository {

    @Query("select c, r from Chunu c left outer join Reply r on r.chunu = c where c.cno = :cno")
    List<Object[]> getChunuwithReply(@Param("cno") Long cno);

    @Query(value = "select c, count(r) " +
            "from Chunu c " +
            "left outer join Reply r " +
            "on r.chunu = c " +
            "group by c",
            countQuery = "select count(c) from Chunu c")
    Page<Object[]> getChunuWithReplyCount(Pageable pageable); // 목록 화면에 필요한 데이터

    @Query("select c, count(r) " +
            "from Chunu c " +
            "left outer join Reply r " +
            "on r.chunu = c " +
            "where c.cno = :cno")
    Object getChunuByCno(@Param("cno") Long cno);

    @Query("select c, ci, avg(coalesce(r.grade, 0)), count(r) " +
            "from Chunu c " +
            "left outer join ChunuImage ci on ci.chunu = c " +
            "left outer join Reply r on r.chunu = c " +
            "where c.cno = :cno " +
            "group by ci")
    List<Object[]> getCno(Long cno);
}
