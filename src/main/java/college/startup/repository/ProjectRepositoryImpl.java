package college.startup.repository;

import college.startup.domain.*;
import college.startup.dto.PageParams;
import college.startup.dto.ProjectPostDTO;
import college.startup.dto.UserStats;
import college.startup.repository.helper.UserStatsQueryHelper;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mauriciosilva on 9/17/16.
 */
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepositoryImpl.class);

    private final JPAQueryFactory queryFactory;


    @Autowired
    public ProjectRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ProjectPostDTO> findAsFeed(User user, PageParams pageParams) {


        final QProject qProject = QProject.project;
        final QUserProfile qUserProfile = QUserProfile.userProfile;
        final QTag qTag = QTag.tag;

        final ConstructorExpression<UserStats> userStatsExpression =
                UserStatsQueryHelper.userStatsExpression(qProject.user, user);

        return queryFactory.selectDistinct(qProject, qProject.user, userStatsExpression)
                .from(qProject, qTag, qUserProfile)
                .innerJoin(qProject.user)
                .where(qProject.user.eq(user).or(qProject.tags.contains(qTag)
                                .and(qUserProfile.tags.contains(qTag))
                                .and(qUserProfile.user.eq(user)))
                                .and(pageParams.getSinceId().map(qProject.id::gt).orElse(null))
                                .and(pageParams.getMaxId().map(qProject.id::lt).orElse(null))
                )
                .orderBy(qProject.id.desc())
                .limit(pageParams.getCount())
                .fetch()
                .stream()
                .map(row -> ProjectPostDTO.builder()
                                .project(row.get(qProject))
                                .user(row.get(qProject.user))
                                .userStats(row.get(userStatsExpression))
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectPostDTO> findAsPublicFeed(User user, PageParams pageParams) {

        QProject qProject = QProject.project;

        final ConstructorExpression<UserStats> userStatsExpression =
                UserStatsQueryHelper.userStatsExpression(qProject.user, user);

        return queryFactory.select(qProject, qProject.user, userStatsExpression)
                .from(qProject)
                .innerJoin(qProject.user)
                .where((qProject.user.ne(user))
                                .and(pageParams.getSinceId().map(qProject.id::gt).orElse(null))
                                .and(pageParams.getMaxId().map(qProject.id::lt).orElse(null))
                )
                .orderBy(qProject.id.desc())
                .limit(pageParams.getCount())
                .fetch()
                .stream()
                .map(row -> ProjectPostDTO.builder()
                                .project(row.get(qProject))
                                .user(row.get(qProject.user))
                                .userStats(row.get(userStatsExpression))
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Project> findByUser(User user, PageParams pageParams) {
        final QProject qProject = QProject.project;
        return queryFactory.selectFrom(qProject)
                .where(qProject.user.eq(user)
                                .and(pageParams.getSinceId().map(qProject.id::gt).orElse(null))
                                .and(pageParams.getMaxId().map(qProject.id::lt).orElse(null))
                )
                .orderBy(qProject.id.desc())
                .limit(pageParams.getCount())
                .fetch();
    }
}
