package cc.sven.springboottemplate.event;

import cc.sven.springboottemplate.event.event.AsyncEvent;
import cc.sven.springboottemplate.event.event.SyncEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalEventListener {

    @Async("AsyncEventExecutor")
    @EventListener(classes = AsyncEvent.class)
    public void handleAsyncEvent(@NonNull AsyncEvent event) {
        log.warn("****** handleAsyncEvent {}", event.getCreationDate());
    }

    @EventListener(classes = SyncEvent.class)
    public void handleSyncEvent(@NonNull SyncEvent event) {
        log.warn("****** handleSyncEvent {} ", event.getCreationDate());
    }

}
