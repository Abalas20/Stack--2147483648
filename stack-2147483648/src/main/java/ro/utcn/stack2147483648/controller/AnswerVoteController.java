package ro.utcn.stack2147483648.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.utcn.stack2147483648.dto.AnswerVoteDTO;
import ro.utcn.stack2147483648.service.AnswerVoteService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AnswerVoteController {

    private final AnswerVoteService answerVoteService;

    public AnswerVoteController(AnswerVoteService answerVoteService) {
        this.answerVoteService = answerVoteService;
    }

    @PostMapping("/voteAnswer")
    public ResponseEntity<?> postVote(@RequestBody AnswerVoteDTO answerVoteDTO) {
        Optional<AnswerVoteDTO> createAnswerVoteDTO = answerVoteService.addAnswerVote(answerVoteDTO);
        if (createAnswerVoteDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("Something went wrong!");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createAnswerVoteDTO);
        }
    }
}
