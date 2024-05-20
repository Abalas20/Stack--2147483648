package ro.utcn.stack2147483648.service;

import ro.utcn.stack2147483648.dto.AnswerDTO;

import java.util.List;
import java.util.Optional;

public interface AnswerService {
    Optional<AnswerDTO> addAnswer(AnswerDTO answerDTO);

    Optional<List<AnswerDTO>> getAnswers(Long questionId);

    Optional<AnswerDTO> getAnswer(Long answerId);

    Optional<AnswerDTO> editAnswer(AnswerDTO answerDTO, Long userId);
}
