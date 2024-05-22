package ro.utcn.stack2147483648.service;

import ro.utcn.stack2147483648.dto.AnswerVoteDTO;

import java.util.Optional;

public interface AnswerVoteService {

    Optional<AnswerVoteDTO> addAnswerVote(AnswerVoteDTO answerVoteDTO);
}
