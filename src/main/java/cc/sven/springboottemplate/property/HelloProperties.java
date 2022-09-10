package cc.sven.springboottemplate.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "hello")
public class HelloProperties {

    private Long maxListItems;

}
