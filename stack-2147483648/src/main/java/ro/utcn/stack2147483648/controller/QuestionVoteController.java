package ro.utcn.stack2147483648.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcn.stack2147483648.dto.QuestionVoteDTO;
import ro.utcn.stack2147483648.service.QuestionVoteService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuestionVoteController {

    private final QuestionVoteService questionVoteService;

    public QuestionVoteController(QuestionVoteService questionVoteService) {
        this.questionVoteService = questionVoteService;
    }

    @PostMapping("/voteQuestion")
    public ResponseEntity<?> postVote(@RequestBody QuestionVoteDTO questionVoteDTO) {
        Optional<QuestionVoteDTO> createQuestionVoteDTO = questionVoteService.addQuestionVote(questionVoteDTO);
        if (createQuestionVoteDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("Something went wrong!");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createQuestionVoteDTO);
        }
    }

    @GetMapping("/voteCount/{questionId}")
    public ResponseEntity<?> getVoteCount(@PathVariable Long questionId) {
        int voteCount = questionVoteService.getVoteCount(questionId);
        return ResponseEntity.ok(voteCount);
    }
}
