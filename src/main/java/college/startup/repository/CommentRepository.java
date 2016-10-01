package college.startup.repository;

import college.startup.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mauriciosilva on 9/20/16.
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom{

}
