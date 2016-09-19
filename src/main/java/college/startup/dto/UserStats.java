package college.startup.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserStats {

    private final long projectsCnt;
    private final long followingCnt;
    private final long followerCnt;
    private final boolean isFollowedByMe;

    @QueryProjection
    public UserStats(long projectsCnt, long followingCnt, long followerCnt, boolean isFollowedByMe) {
        this.projectsCnt = projectsCnt;
        this.followingCnt = followingCnt;
        this.followerCnt = followerCnt;
        this.isFollowedByMe = isFollowedByMe;
    }

}
