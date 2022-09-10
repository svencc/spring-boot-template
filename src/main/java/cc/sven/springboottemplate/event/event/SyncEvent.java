package cc.sven.springboottemplate.event.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
public class SyncEvent {

    @NonNull
    private final Date creationDate = new Date();

}
