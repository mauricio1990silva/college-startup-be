package college.startup.repository;

import college.startup.domain.*;
import college.startup.dto.MessageDTO;
import college.startup.dto.PageParams;
import college.startup.dto.UserStats;
import college.startup.repository.helper.UserStatsQueryHelper;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mauriciosilva on 9/20/16.
 */
public class MessageRepositoryImpl implements MessageRepositoryCustom{

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(MembershipRepositoryImpl.class);

    private final JPAQueryFactory queryFactory;
    private final QUser qUser = QUser.user;
    private final QMessage qMessage = QMessage.message;


    @Autowired
    public MessageRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<MessageDTO> findMessages(User currentUser, Project project, PageParams pageParams) {

        final ConstructorExpression<UserStats> userStatsExpression =
                UserStatsQueryHelper.userStatsExpression(qUser, currentUser);

        return queryFactory.select(qUser, qMessage, userStatsExpression)
                .from(qUser)
                .innerJoin(qUser.messages, qMessage)
                .where(qMessage.project.eq(project)
                                .and(pageParams.getSinceId().map(qMessage.id::gt).orElse(null))
                                .and(pageParams.getMaxId().map(qMessage.id::lt).orElse(null))
                )
                .orderBy(qMessage.id.desc())
                .limit(pageParams.getCount())
                .fetch()
                .stream()
                .map(row -> MessageDTO.builder()
                        .user(row.get(qUser))
                        .message(row.get(qMessage))
                        .userStats(row.get(userStatsExpression))
                        .build())
                .collect(Collectors.toList());

    }

}
