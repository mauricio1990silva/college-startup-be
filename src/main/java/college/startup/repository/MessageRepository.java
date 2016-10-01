package college.startup.repository;

import college.startup.domain.Comment;
import college.startup.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mauriciosilva on 9/20/16.
 */
public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

}
