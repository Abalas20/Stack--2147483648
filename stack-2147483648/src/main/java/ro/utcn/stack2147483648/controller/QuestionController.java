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

    @PutMapping("/question/{userId}")
    public ResponseEntity<?> updateQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable Long userId) {
        Optional<QuestionDTO> updatedQuestionDTO = questionService.updateQuestion(questionDTO, userId);
        if (updatedQuestionDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("Update failed!");
        } else {
            return ResponseEntity.ok(updatedQuestionDTO);
        }
    }


}
