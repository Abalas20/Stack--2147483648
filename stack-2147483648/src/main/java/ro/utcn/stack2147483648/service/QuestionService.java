package ro.utcn.stack2147483648.service;

import ro.utcn.stack2147483648.dto.AllQuestionResponseDTO;
import ro.utcn.stack2147483648.dto.QuestionDTO;
import ro.utcn.stack2147483648.entities.Tag;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    Optional<QuestionDTO> addQuestion(QuestionDTO questionDTO);

    List<Tag> getAllTags();

    AllQuestionResponseDTO getAllQuestions(int pageNumber);

    Optional<QuestionDTO> getQuestion(Long questionId);

    Optional<QuestionDTO> updateQuestion(QuestionDTO questionDTO, Long userId);
}
