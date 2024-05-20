package ro.utcn.stack2147483648.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcn.stack2147483648.entities.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
