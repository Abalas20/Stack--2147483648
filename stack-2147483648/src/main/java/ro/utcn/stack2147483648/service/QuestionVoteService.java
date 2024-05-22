package ro.utcn.stack2147483648.service;

import ro.utcn.stack2147483648.dto.QuestionVoteDTO;

import java.util.Optional;

public interface QuestionVoteService {
    Optional<QuestionVoteDTO> addQuestionVote(QuestionVoteDTO questionVoteDTO);

    int getVoteCount(Long questionId);
}
