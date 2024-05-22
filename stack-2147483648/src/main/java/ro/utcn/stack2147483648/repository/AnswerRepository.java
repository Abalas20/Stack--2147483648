package ro.utcn.stack2147483648.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.utcn.stack2147483648.entities.Answer;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<List<Answer>> findAllByQuestionIdOrderByVoteCountDesc(Long questionId);
    Optional<Answer> findById(Long id);
}
