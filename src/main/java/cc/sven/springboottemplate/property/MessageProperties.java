package cc.sven.springboottemplate.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "message")
public class MessageProperties {

    private Long maxListItems;
    @Builder.Default
    private List<String> validDomains = new ArrayList<>();

}
