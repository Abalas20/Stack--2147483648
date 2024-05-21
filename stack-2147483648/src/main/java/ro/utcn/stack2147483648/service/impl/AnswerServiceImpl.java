package ro.utcn.stack2147483648.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.stack2147483648.dto.AnswerDTO;
import ro.utcn.stack2147483648.entities.Answer;
import ro.utcn.stack2147483648.entities.Question;
import ro.utcn.stack2147483648.entities.User;
import ro.utcn.stack2147483648.repository.AnswerRepository;
import ro.utcn.stack2147483648.repository.QuestionRepository;
import ro.utcn.stack2147483648.repository.UserRepository;
import ro.utcn.stack2147483648.service.AnswerService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static ro.utcn.stack2147483648.utils.DateConverter.getCreationDate;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<AnswerDTO> addAnswer(AnswerDTO answerDTO) {
        Optional<User> user = userRepository.findById(answerDTO.getUserId());
        Optional<Question> question = questionRepository.findById(answerDTO.getQuestionId());
        if (user.isPresent() && question.isPresent()) {
            Answer answer = new Answer();
            answer.setBody(answerDTO.getBody());
            answer.setCreatedDate(new Date());
            answer.setAuthor(user.get());
            answer.setQuestion(question.get());
            answer.setUrl(answerDTO.getUrl());
            Answer savedAnswer = answerRepository.save(answer);
            return Optional.of(new AnswerDTO(savedAnswer.getId(), savedAnswer.getBody(), getCreationDate(savedAnswer.getCreatedDate()), savedAnswer.getAuthor().getId(), savedAnswer.getQuestion().getId(), savedAnswer.getUrl(), user.get().getUsername()));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<List<AnswerDTO>> getAnswers(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId).map(answers -> answers.stream()
                .map(answer -> new AnswerDTO(answer.getId(), answer.getBody(), getCreationDate(answer.getCreatedDate()), answer.getAuthor().getId(), answer.getQuestion().getId(),answer.getUrl(), answer.getAuthor().getUsername()))
                .toList());
    }

    @Override
    public Optional<AnswerDTO> getAnswer(Long answerId) {
        return answerRepository.findById(answerId).map(answer -> new AnswerDTO(answer.getId(), answer.getBody(), getCreationDate(answer.getCreatedDate()), answer.getAuthor().getId(), answer.getQuestion().getId(), answer.getUrl(), answer.getAuthor().getUsername()));
    }

    @Override
    public Optional<AnswerDTO> editAnswer(AnswerDTO answerDTO, Long userId) {
        Optional<Answer> answer = answerRepository.findById(answerDTO.getId());
        if (answer.isPresent() && answer.get().getAuthor().getId().equals(userId)) {
            answer.get().setBody(answerDTO.getBody());
            answer.get().setUrl(answerDTO.getUrl());
            Answer editedAnswer = answerRepository.save(answer.get());
            return Optional.of(new AnswerDTO(editedAnswer.getId(), editedAnswer.getBody(), getCreationDate(editedAnswer.getCreatedDate()), editedAnswer.getAuthor().getId(), editedAnswer.getQuestion().getId(), editedAnswer.getUrl(), editedAnswer.getAuthor().getUsername()));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteAnswer(Long answerId, Long userId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (answer.isPresent() && answer.get().getAuthor().getId().equals(userId)) {
            answerRepository.delete(answer.get());
            return true;
        } else {
            return false;
        }
    }


}
