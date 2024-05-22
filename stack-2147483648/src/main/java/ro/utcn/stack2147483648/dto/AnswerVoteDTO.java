package ro.utcn.stack2147483648.dto;

import lombok.Data;

@Data
public class AnswerVoteDTO {
    private Long id;
    private Long answerId;
    private Long userId;
    private int value;
}
