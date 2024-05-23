package ro.utcn.stack2147483648.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcn.stack2147483648.dto.AllQuestionResponseDTO;
import ro.utcn.stack2147483648.dto.AnswerDTO;
import ro.utcn.stack2147483648.dto.QuestionDTO;
import ro.utcn.stack2147483648.dto.SingleQuestionDTO;
import ro.utcn.stack2147483648.entities.Tag;
import ro.utcn.stack2147483648.service.AnswerService;
import ro.utcn.stack2147483648.service.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping("/question")
    public ResponseEntity<?> postQuestion(@RequestBody QuestionDTO questionDTO) {
        Optional<QuestionDTO> createdQuestionDTO = questionService.addQuestion(questionDTO);
        if (createdQuestionDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("Something went wrong!");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
        }
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = questionService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/questions/{pageNumber}")
    public ResponseEntity<AllQuestionResponseDTO> getAllQuestions(@PathVariable int pageNumber) {
        AllQuestionResponseDTO allQuestionResponseDTO = questionService.getAllQuestions(pageNumber);
        return ResponseEntity.ok(allQuestionResponseDTO);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable Long questionId) {
        Optional<QuestionDTO> questionDTO = questionService.getQuestion(questionId);
        Optional<List<AnswerDTO>> answerDTOList = answerService.getAnswers(questionId);
        if (questionDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("Question not found!");
        } else {
            SingleQuestionDTO singleQuestionDTO = new SingleQuestionDTO();
            singleQuestionDTO.setQuestion(questionDTO.get());
            singleQuestionDTO.setAnswers(answerDTOList.orElse(null));
            return ResponseEntity.ok(singleQuestionDTO);
        }
    }

    @PutMapping("/question/{userId}/{role}")
    public ResponseEntity<?> updateQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable Long userId, @PathVariable String role) {
        Optional<QuestionDTO> updatedQuestionDTO = questionService.updateQuestion(questionDTO, userId, role);
        if (updatedQuestionDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("Update failed!");
        } else {
            return ResponseEntity.ok(updatedQuestionDTO);
        }
    }

    @DeleteMapping("/question/{questionId}/{userId}/{role}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId, @PathVariable Long userId, @PathVariable String role) {
        if (questionService.deleteQuestion(questionId, userId, role)) {
            return ResponseEntity.ok("Question deleted!");
        } else {
            return ResponseEntity.badRequest().body("Question not found!");
        }
    }

    @GetMapping("/questions/search-by-text/{text}/{pageNumber}")
    public ResponseEntity<AllQuestionResponseDTO> searchQuestionsByText(@PathVariable String text, @PathVariable int pageNumber) {
        AllQuestionResponseDTO allQuestionResponseDTO = questionService.searchQuestionsByText(text, pageNumber);
        return ResponseEntity.ok(allQuestionResponseDTO);
    }

    @GetMapping("/questions/search-by-username/{username}/{pageNumber}")
    public ResponseEntity<AllQuestionResponseDTO> searchQuestionsByUsername(@PathVariable String username, @PathVariable int pageNumber) {
        AllQuestionResponseDTO allQuestionResponseDTO = questionService.searchQuestionsByUsername(username, pageNumber);
        return ResponseEntity.ok(allQuestionResponseDTO);
    }

    @GetMapping("/questions/search-by-tag/{tag}/{pageNumber}")
    public ResponseEntity<AllQuestionResponseDTO> searchQuestionsByTag(@PathVariable String tag, @PathVariable int pageNumber) {
        AllQuestionResponseDTO allQuestionResponseDTO = questionService.searchQuestionsByTag(tag, pageNumber);
        return ResponseEntity.ok(allQuestionResponseDTO);
    }

}
