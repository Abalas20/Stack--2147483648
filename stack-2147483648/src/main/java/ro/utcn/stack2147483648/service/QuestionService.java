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
    boolean deleteQuestion(Long questionId, Long userId);
    AllQuestionResponseDTO searchQuestionsByText(String text, int pageNumber);
    AllQuestionResponseDTO searchQuestionsByUsername(String username, int pageNumber);
    AllQuestionResponseDTO searchQuestionsByTag(String tag, int pageNumber);
}
