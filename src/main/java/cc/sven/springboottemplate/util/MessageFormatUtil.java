package cc.sven.springboottemplate.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class MessageFormatUtil {

    @NonNull
    public String format(@NonNull String name, @NonNull Date created) {
        return String.format(
                "Hello %1s. It is %2ta %3tb %4td %5tT %6tZ %7tY",
                name,
                created,
                created,
                created,
                created,
                created,
                created
        );
    }

    @NonNull
    public String parseDomain(@NonNull String recipient) {
        return recipient.toLowerCase().substring(recipient.lastIndexOf("@") + 1);
    }

}
