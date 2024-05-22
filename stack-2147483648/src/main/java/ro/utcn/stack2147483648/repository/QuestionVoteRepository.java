package ro.utcn.stack2147483648.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.utcn.stack2147483648.entities.QuestionVote;

import java.util.List;
import java.util.Optional;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {
    Optional<QuestionVote> findByQuestionIdAndUserId(Long questionId, Long userId);
    List<QuestionVote> findAllByQuestionId(Long questionId);
}
