package ro.utcn.stack2147483648.service;

import ro.utcn.stack2147483648.dto.QuestionDTO;
import ro.utcn.stack2147483648.entities.Tag;

import java.util.List;

public interface QuestionService {

    QuestionDTO addQuestion(QuestionDTO questionDTO);

    List<Tag> getAllTags();
}
