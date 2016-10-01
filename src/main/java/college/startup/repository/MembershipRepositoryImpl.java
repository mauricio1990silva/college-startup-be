package college.startup.repository;

import college.startup.domain.Project;
import college.startup.domain.QMessage;
import college.startup.domain.QUser;
import college.startup.domain.User;
import college.startup.dto.MessageDTO;
import college.startup.dto.PageParams;
import college.startup.dto.UserStats;
import college.startup.repository.helper.UserStatsQueryHelper;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mauriciosilva on 9/25/16.
 */
public class MembershipRepositoryImpl implements MembershipRepositoryCustom {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(MembershipRepositoryImpl.class);

    private final JPAQueryFactory queryFactory;


    @Autowired
    public MembershipRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
