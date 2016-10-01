package college.startup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by mauriciosilva on 9/20/16.
 */

@Data
@ToString
@EqualsAndHashCode
public class CommentParams {

    private final String content;

    public CommentParams(@JsonProperty("content") String content){
        this.content = content;
    }
}
