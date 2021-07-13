package codehouse.premakeblog.repository.search;

import codehouse.premakeblog.entity.Chunu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchChunuRepository {
    Chunu search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
