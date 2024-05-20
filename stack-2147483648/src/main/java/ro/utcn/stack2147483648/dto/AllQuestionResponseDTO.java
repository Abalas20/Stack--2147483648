package ro.utcn.stack2147483648.dto;

import lombok.Data;

import java.util.List;

@Data
public class AllQuestionResponseDTO {
    private List<QuestionDTO> questions;
    private Integer totalPages;
    private Integer pageNumber;
}
