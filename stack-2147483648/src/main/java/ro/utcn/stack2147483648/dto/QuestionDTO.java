package ro.utcn.stack2147483648.dto;

import lombok.Data;
import ro.utcn.stack2147483648.entities.Tag;

import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String body;
    private List<Tag> tags;
    private Long userId;
    private String author;
    private String creationDate;
    private String url;
}
