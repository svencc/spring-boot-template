package cc.sven.springboottemplate.event.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
public class AsyncEvent {

    @NonNull
    private final Date creationDate = new Date();

}
