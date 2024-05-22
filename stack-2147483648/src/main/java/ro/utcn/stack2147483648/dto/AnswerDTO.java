package ro.utcn.stack2147483648.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AnswerDTO {
    private Long id;
    private String body;
    private Long userId;
    private String createdDate;
    private Long questionId;
    private String url;
    private String author;
    private int voteCount;

    public AnswerDTO(Long id, String body, String createdDate, Long id1, Long id2, String url, String author, int voteCount) {
        this.id = id;
        this.body = body;
        this.createdDate = createdDate;
        this.userId = id1;
        this.questionId = id2;
        this.url = url;
        this.author = author;
        this.voteCount = voteCount;
    }
}
