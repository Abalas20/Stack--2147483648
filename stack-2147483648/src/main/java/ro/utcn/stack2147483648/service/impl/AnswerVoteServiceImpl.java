package ro.utcn.stack2147483648.service.impl;

import org.springframework.stereotype.Service;
import ro.utcn.stack2147483648.dto.AnswerVoteDTO;
import ro.utcn.stack2147483648.entities.Answer;
import ro.utcn.stack2147483648.entities.AnswerVote;
import ro.utcn.stack2147483648.entities.User;
import ro.utcn.stack2147483648.repository.AnswerRepository;
import ro.utcn.stack2147483648.repository.AnswerVoteRepository;
import ro.utcn.stack2147483648.repository.UserRepository;
import ro.utcn.stack2147483648.service.AnswerVoteService;

import java.util.Optional;

@Service
public class AnswerVoteServiceImpl implements AnswerVoteService {

    private final AnswerVoteRepository answerVoteRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public AnswerVoteServiceImpl(AnswerVoteRepository answerVoteRepository, AnswerRepository answerRepository, UserRepository userRepository) {
        this.answerVoteRepository = answerVoteRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<AnswerVoteDTO> addAnswerVote(AnswerVoteDTO answerVoteDTO) {
        User user = userRepository.findById(answerVoteDTO.getUserId()).orElseThrow();
        Answer answer = answerRepository.findById(answerVoteDTO.getAnswerId()).orElseThrow();
        if(!canVote(user, answer)) {
            return Optional.empty();
        }
        answer.setVoteCount(answer.getVoteCount() + answerVoteDTO.getValue());
        Answer updatedAnswer = answerRepository.save(answer);
        AnswerVote answerVote = new AnswerVote();
        answerVote.setAnswer(updatedAnswer);
        answerVote.setUser(user);
        answerVote.setValue(answerVoteDTO.getValue());
        AnswerVote createAnswerVote = answerVoteRepository.save(answerVote);

        return convertToDTO(createAnswerVote);
    }

    private boolean canVote(User user, Answer answer) {
        if (answerVoteRepository.findByAnswerIdAndUserId(answer.getId(), user.getId()).isPresent()) {
            return false;
        }
        return !answer.getAuthor().getId().equals(user.getId());
    }


    public Optional<AnswerVoteDTO> convertToDTO(AnswerVote answerVote) {
        if (answerVote != null) {
            AnswerVoteDTO dto = new AnswerVoteDTO();
            dto.setId(answerVote.getId());
            dto.setAnswerId(answerVote.getAnswer().getId());
            dto.setUserId(answerVote.getUser().getId());
            dto.setValue(answerVote.getValue());
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }
}
