package college.startup.repository.helper;


import college.startup.domain.QProject;
import college.startup.domain.QRelationship;
import college.startup.domain.QUser;
import college.startup.domain.User;
import college.startup.dto.UserStats;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import java.util.Optional;

public class UserStatsQueryHelper {

    public static ConstructorExpression<UserStats> userStatsExpression(QUser qUser, User currentUser) {
        return Projections.constructor(UserStats.class,
                cntProjectsQuery(qUser),
                cntFollowingsQuery(qUser),
                cntFollowersQuery(qUser),
                isFollowedByMeQuery(qUser, currentUser)
        );
    }

    private static JPQLQuery<Boolean> isFollowedByMeQuery(QUser qUser, User currentUser) {
        final QRelationship qRelationship = new QRelationship("relationship_is_followed_by_me");
        return JPAExpressions.select(qRelationship.count().eq(1L))
                .from(qRelationship)
                .where(qRelationship.followed.eq(qUser)
                        .and(Optional.ofNullable(currentUser)
                                .map(qRelationship.follower::eq)
                                .orElse(qRelationship.ne(qRelationship)) // make it always false when no current user
                        )
                );
    }

    private static JPQLQuery<Long> cntFollowersQuery(QUser qUser) {
        final QRelationship qRelationship = new QRelationship("relationship_cnt_followers");
        return JPAExpressions.select(qRelationship.count())
                .from(qRelationship)
                .where(qRelationship.followed.eq(qUser));
    }

    private static JPQLQuery<Long> cntFollowingsQuery(QUser qUser) {
        final QRelationship qRelationship = new QRelationship("relationship_cnt_followings");
        return JPAExpressions.select(qRelationship.count())
                .from(qRelationship)
                .where(qRelationship.follower.eq(qUser));
    }

    private static JPQLQuery<Long> cntProjectsQuery(QUser qUser) {
        final QProject qMicropost = new QProject("project_cnt_posts");
        return JPAExpressions.select(qMicropost.count())
                .from(qMicropost)
                .where(qMicropost.user.eq(qUser));
    }
}
