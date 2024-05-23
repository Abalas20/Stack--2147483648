package ro.utcn.stack2147483648.service.impl;

import org.springframework.stereotype.Service;
import ro.utcn.stack2147483648.dto.QuestionVoteDTO;
import ro.utcn.stack2147483648.entities.Question;
import ro.utcn.stack2147483648.entities.QuestionVote;
import ro.utcn.stack2147483648.entities.User;
import ro.utcn.stack2147483648.repository.QuestionRepository;
import ro.utcn.stack2147483648.repository.QuestionVoteRepository;
import ro.utcn.stack2147483648.repository.UserRepository;
import ro.utcn.stack2147483648.service.QuestionVoteService;

import java.util.Optional;

@Service
public class QuestionVoteServiceImpl implements QuestionVoteService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final QuestionVoteRepository questionVoteRepository;

    public QuestionVoteServiceImpl(QuestionRepository questionRepository, UserRepository userRepository, QuestionVoteRepository questionVoteRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.questionVoteRepository = questionVoteRepository;
    }

    @Override
    public Optional<QuestionVoteDTO> addQuestionVote(QuestionVoteDTO questionVoteDTO) {
        User user = userRepository.findById(questionVoteDTO.getUserId()).orElseThrow();
        Question question = questionRepository.findById(questionVoteDTO.getQuestionId()).orElseThrow();
        if(!canVote(user, question)) {
            return Optional.empty();
        }
        QuestionVote questionVote = new QuestionVote();
        questionVote.setQuestion(question);
        questionVote.setUser(user);
        questionVote.setValue(questionVoteDTO.getValue());
        QuestionVote createQuestionVote = questionVoteRepository.save(questionVote);

        //Update user score for question author
        if (questionVote.getValue() == 1) {
            updateUserScore(question.getAuthor().getId(), 2.5);
        } else {
            updateUserScore(question.getAuthor().getId(), -1.5);
        }

        return convertToDTO(createQuestionVote);
    }

    @Override
    public int getVoteCount(Long questionId) {
        return questionVoteRepository.findAllByQuestionId(questionId).stream().mapToInt(QuestionVote::getValue).sum();
    }

    private void updateUserScore(long userId, double value) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setScore(user.getScore() + value);
        userRepository.save(user);
    }

    private boolean canVote(User user, Question question) {
        if (questionVoteRepository.findByQuestionIdAndUserId(question.getId(), user.getId()).isPresent()) {
            return false;
        }
        return !question.getAuthor().getId().equals(user.getId());
    }

    public Optional<QuestionVoteDTO> convertToDTO(QuestionVote questionVote) {
        if (questionVote != null) {
            QuestionVoteDTO dto = new QuestionVoteDTO();
            dto.setId(questionVote.getId());
            dto.setQuestionId(questionVote.getQuestion().getId());
            dto.setUserId(questionVote.getUser().getId());
            dto.setValue(questionVote.getValue());
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }
}
