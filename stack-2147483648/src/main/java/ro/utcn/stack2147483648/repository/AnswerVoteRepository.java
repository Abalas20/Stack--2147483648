package ro.utcn.stack2147483648.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.utcn.stack2147483648.entities.AnswerVote;

import java.util.List;
import java.util.Optional;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
    Optional<AnswerVote> findByAnswerIdAndUserId(Long answerId, Long userId);
    List<AnswerVote> findAllByAnswerId(Long answerId);
}
