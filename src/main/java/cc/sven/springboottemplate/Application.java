package cc.sven.springboottemplate;

import cc.sven.springboottemplate.property.MessageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        MessageProperties.class
//        ,HelloProperties2.class
//        ,....

})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
