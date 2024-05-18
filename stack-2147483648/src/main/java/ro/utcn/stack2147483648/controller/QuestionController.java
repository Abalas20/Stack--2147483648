package ro.utcn.stack2147483648.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcn.stack2147483648.dto.QuestionDTO;
import ro.utcn.stack2147483648.entities.Tag;
import ro.utcn.stack2147483648.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/question")
    public ResponseEntity<?> postQuestion(@RequestBody QuestionDTO questionDTO) {
       QuestionDTO createdQuestionDTO = questionService.addQuestion(questionDTO);
        if (createdQuestionDTO == null) {
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

    @GetMapping("/question")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

}
