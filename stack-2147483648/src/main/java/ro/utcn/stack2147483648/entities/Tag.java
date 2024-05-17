package ro.utcn.stack2147483648.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tag")
@Data
public class Tag {

    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String tagName;
}
