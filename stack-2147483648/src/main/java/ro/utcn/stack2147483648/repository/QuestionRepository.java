package ro.utcn.stack2147483648.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcn.stack2147483648.entities.Question;
import ro.utcn.stack2147483648.entities.User;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByOrderByCreatedDateDesc(Pageable pageable);
    Page<Question> findAllByTitleContainingOrBodyContainingOrderByCreatedDateDesc(String title, String body, Pageable pageable);
    Page<Question> findAllByAuthorOrderByCreatedDateDesc(User user, Pageable paging);
    Page<Question> findAllByTags_TagNameOrderByCreatedDateDesc(String tag, Pageable paging);
}


