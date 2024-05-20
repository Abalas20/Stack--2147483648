package ro.utcn.stack2147483648.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcn.stack2147483648.dto.AnswerDTO;
import ro.utcn.stack2147483648.service.AnswerService;

import java.util.Optional;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<?> postAnswer(@RequestBody AnswerDTO answerDTO) {
        Optional<AnswerDTO> createdAnswerDTO = answerService.addAnswer(answerDTO);
        if (createdAnswerDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("Something went wrong!");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerDTO);
        }
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<?> getAnswer(@PathVariable Long answerId) {
        Optional<AnswerDTO> answerDTO = answerService.getAnswer(answerId);
        if (answerDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("Answer not found!");
        } else {
            return ResponseEntity.ok(answerDTO.get());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> editAnswer(@RequestBody AnswerDTO answerDTO, @PathVariable Long userId) {
        Optional<AnswerDTO> editedAnswerDTO = answerService.editAnswer(answerDTO, userId);
        if (editedAnswerDTO.isEmpty()) {
            return ResponseEntity.badRequest().body("Something went wrong!");
        } else {
            return ResponseEntity.ok(editedAnswerDTO.get());
        }
    }
}
