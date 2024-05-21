package ro.utcn.stack2147483648.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.stack2147483648.dto.AllQuestionResponseDTO;
import ro.utcn.stack2147483648.dto.QuestionDTO;
import ro.utcn.stack2147483648.entities.Question;
import ro.utcn.stack2147483648.entities.Tag;
import ro.utcn.stack2147483648.entities.User;
import ro.utcn.stack2147483648.exception.UserNotFoundException;
import ro.utcn.stack2147483648.repository.QuestionRepository;
import ro.utcn.stack2147483648.repository.TagRepository;
import ro.utcn.stack2147483648.repository.UserRepository;
import ro.utcn.stack2147483648.service.QuestionService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ro.utcn.stack2147483648.utils.DateConverter.getCreationDate;

@Service
public class QuestionServiceImpl implements QuestionService {

    public static final int PAGE_SIZE = 5;

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Optional<QuestionDTO> addQuestion(final QuestionDTO questionDTO) {
        return userRepository.findById(questionDTO.getUserId())
                .map(user -> {
                    Question question = new Question();
                    question.setTitle(questionDTO.getTitle());
                    question.setBody(questionDTO.getBody());
                    question.setCreatedDate(new Date());
                    question.setUrl(questionDTO.getUrl());

                    List<Tag> tags = questionDTO.getTags().stream()
                            .map(tagDto -> tagRepository.findByTagName(tagDto.getTagName())
                                    .orElseGet(() -> createAndSaveTag(tagDto.getTagName())))
                            .collect(Collectors.toList());

                    question.setTags(tags);
                    question.setAuthor(user);
                    question = questionRepository.save(question);
                    return mapToDTO(question);
                });
    }

    private Tag createAndSaveTag(String tagName) {
        Tag newTag = new Tag();
        newTag.setTagName(tagName);
        return tagRepository.save(newTag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional
    public AllQuestionResponseDTO getAllQuestions(final int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, PAGE_SIZE);
        Page<Question> questionsPage = questionRepository.findAllByOrderByCreatedDateDesc(paging);
        AllQuestionResponseDTO allQuestionResponseDTO = new AllQuestionResponseDTO();
        allQuestionResponseDTO.setQuestions(getQuestionListDto(questionsPage));
        allQuestionResponseDTO.setPageNumber(questionsPage.getNumber());
        allQuestionResponseDTO.setTotalPages(questionsPage.getTotalPages());
        return allQuestionResponseDTO;
    }

    @Override
    public Optional<QuestionDTO> getQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .map(this::mapToDTO);
    }

    @Override
    @Transactional
    public Optional<QuestionDTO> updateQuestion(QuestionDTO questionDTO, Long userId) {
        return questionRepository.findById(questionDTO.getId())
                .filter(question -> question.getAuthor().getId().equals(userId))
                .map(question -> {
                    question.setTitle(questionDTO.getTitle());
                    question.setBody(questionDTO.getBody());

                    List<Tag> tags = questionDTO.getTags().stream()
                            .map(tagDto -> tagRepository.findByTagName(tagDto.getTagName())
                                    .orElseGet(() -> createAndSaveTag(tagDto.getTagName())))
                            .collect(Collectors.toList());

                    question.setTags(tags);
                    question.setUrl(questionDTO.getUrl());
                    question = questionRepository.save(question);
                    return mapToDTO(question);
                });
    }

    @Override
    public boolean deleteQuestion(Long questionId, Long userId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent() && question.get().getAuthor().getId().equals(userId)) {
            questionRepository.delete(question.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public AllQuestionResponseDTO searchQuestionsByText(String text, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, PAGE_SIZE);
        Page<Question> questionsPage = questionRepository.findAllByTitleContainingOrBodyContainingOrderByCreatedDateDesc(text, text, paging);
        AllQuestionResponseDTO allQuestionResponseDTO = new AllQuestionResponseDTO();
        allQuestionResponseDTO.setQuestions(getQuestionListDto(questionsPage));
        allQuestionResponseDTO.setPageNumber(questionsPage.getNumber());
        allQuestionResponseDTO.setTotalPages(questionsPage.getTotalPages());

        return allQuestionResponseDTO;
    }

    @Override
    public AllQuestionResponseDTO searchQuestionsByUsername(String username, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, PAGE_SIZE);
        Optional<User> user = userRepository.findFirstByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        Page<Question> questionsPage = questionRepository.findAllByAuthorOrderByCreatedDateDesc(user.get(), paging);
        AllQuestionResponseDTO allQuestionResponseDTO = new AllQuestionResponseDTO();
        allQuestionResponseDTO.setQuestions(getQuestionListDto(questionsPage));
        allQuestionResponseDTO.setPageNumber(questionsPage.getNumber());
        allQuestionResponseDTO.setTotalPages(questionsPage.getTotalPages());

        return allQuestionResponseDTO;
    }

    @Override
    public AllQuestionResponseDTO searchQuestionsByTag(String tag, int pageNumber) {
        Pageable paging = PageRequest.of(pageNumber, PAGE_SIZE);
        Page<Question> questionsPage = questionRepository.findAllByTags_TagNameOrderByCreatedDateDesc(tag, paging);
        AllQuestionResponseDTO allQuestionResponseDTO = new AllQuestionResponseDTO();
        allQuestionResponseDTO.setQuestions(getQuestionListDto(questionsPage));
        allQuestionResponseDTO.setPageNumber(questionsPage.getNumber());
        allQuestionResponseDTO.setTotalPages(questionsPage.getTotalPages());

        return allQuestionResponseDTO;
    }

    private QuestionDTO mapToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setBody(question.getBody());
        dto.setTags(question.getTags());
        dto.setUserId(question.getAuthor().getId());
        dto.setCreationDate(getCreationDate(question.getCreatedDate()));
        dto.setAuthor(question.getAuthor().getUsername());
        dto.setUrl(question.getUrl());
        return dto;
    }

    private List<QuestionDTO> getQuestionListDto(Page<Question> questionsPage) {
        return questionsPage.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}