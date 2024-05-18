package ro.utcn.stack2147483648.service.impl;

import org.springframework.stereotype.Service;
import ro.utcn.stack2147483648.dto.QuestionDTO;
import ro.utcn.stack2147483648.entities.Question;
import ro.utcn.stack2147483648.entities.Tag;
import ro.utcn.stack2147483648.repository.QuestionRepository;
import ro.utcn.stack2147483648.repository.TagRepository;
import ro.utcn.stack2147483648.repository.UserRepository;
import ro.utcn.stack2147483648.service.QuestionService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public QuestionDTO addQuestion(QuestionDTO questionDTO) {
        return userRepository.findById(questionDTO.getUserId())
                .map(user -> {
                    Question question = new Question();
                    question.setTitle(questionDTO.getTitle());
                    question.setBody(questionDTO.getBody());
                    question.setCreatedDate(new Date());

                    List<Tag> tags = questionDTO.getTags().stream()
                            .map(tagDto -> tagRepository.findByTagName(tagDto.getTagName())
                                    .orElseGet(() -> {
                                        Tag newTag = new Tag();
                                        newTag.setTagName(tagDto.getTagName());
                                        return tagRepository.save(newTag);
                                    }))
                            .collect(Collectors.toList());

                    question.setTags(tags);
                    question.setAuthor(user);
                    question = questionRepository.save(question);
                    return mapToDTO(question);
                })
                .orElse(null);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private QuestionDTO mapToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setBody(question.getBody());
        dto.setTags(question.getTags());
        dto.setUserId(question.getAuthor().getId());
        return dto;
    }
}
