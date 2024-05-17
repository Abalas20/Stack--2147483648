package ro.utcn.stack2147483648.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.utcn.stack2147483648.entities.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, String> {
    Optional<Tag> findByTagName(String tagName);
}
