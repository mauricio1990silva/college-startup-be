package college.startup.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = FirebaseConfig.PREFIX)
@Data
public class FirebaseConfig {

    public static final String PREFIX = "firebase";

    private String serverKey;
    private String senderId;
    private String serverUrl;
    private String serverNotificationUrl;
}
