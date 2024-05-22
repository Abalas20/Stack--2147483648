package ro.utcn.stack2147483648.dto;

import lombok.Data;

@Data
public class QuestionVoteDTO {
    private Long id;
    private Long questionId;
    private Long userId;
    private int value;
}
