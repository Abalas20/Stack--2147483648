package ro.utcn.stack2147483648.dto;

import lombok.Data;

import java.util.List;

@Data
public class SingleQuestionDTO {
    private QuestionDTO question;
    private List<AnswerDTO> answers;
}
