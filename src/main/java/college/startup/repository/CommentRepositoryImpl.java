package college.startup.repository;

import college.startup.domain.Project;
import college.startup.domain.QComment;
import college.startup.domain.User;
import college.startup.dto.*;
import college.startup.repository.helper.UserStatsQueryHelper;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mauriciosilva on 9/20/16.
 */
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CommentRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<CommentDTO> findComments (User user, Project project, PageParams pageParams) {
        QComment qComment = QComment.comment;
        final ConstructorExpression<UserStats> userStatsExpression =
                UserStatsQueryHelper.userStatsExpression(qComment.user, user);

        return queryFactory.select(qComment, qComment.user, userStatsExpression)
                .from(qComment)
                .innerJoin(qComment.user)
                .where((qComment.project.eq(project))
                                .and(pageParams.getSinceId().map(qComment.id::gt).orElse(null))
                                .and(pageParams.getMaxId().map(qComment.id::lt).orElse(null))
                )
                .orderBy(qComment.id.desc())
                .limit(pageParams.getCount())
                .fetch()
                .stream()
                .map(row -> CommentDTO.builder()
                                .comment(row.get(qComment))
                                .user(row.get(qComment.user))
                                .userStats(row.get(userStatsExpression))
                                .build()
                )
                .collect(Collectors.toList());
    }
}
