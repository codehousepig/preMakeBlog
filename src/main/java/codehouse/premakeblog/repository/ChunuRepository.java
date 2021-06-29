package codehouse.premakeblog.repository;

import codehouse.premakeblog.entity.Chunu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ChunuRepository extends JpaRepository<Chunu, Long>, QuerydslPredicateExecutor<Chunu> {
}
