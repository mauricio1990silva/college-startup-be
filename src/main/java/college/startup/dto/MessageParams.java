package college.startup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by mauriciosilva on 9/20/16.
 */

@Data
@ToString
@EqualsAndHashCode
public class MessageParams {

    private final String content;

    public MessageParams(@JsonProperty("content") String content){
        this.content = content;
    }
}
