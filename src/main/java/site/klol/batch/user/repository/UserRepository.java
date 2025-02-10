package site.klol.batch.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.klol.batch.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
