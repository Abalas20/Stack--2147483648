package ro.utcn.stack2147483648.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.utcn.stack2147483648.entities.Question;
import ro.utcn.stack2147483648.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

    Optional<User> findFirstByUsername(String author);

    Page<User> findAllByOrderByScoreDesc(Pageable paging);
}
