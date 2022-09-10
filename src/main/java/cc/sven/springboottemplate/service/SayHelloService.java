package cc.sven.springboottemplate.service;

import cc.sven.springboottemplate.dto.HelloMessageDto;
import cc.sven.springboottemplate.entity.HelloMessage;
import cc.sven.springboottemplate.event.event.SyncEvent;
import cc.sven.springboottemplate.property.MessageProperties;
import cc.sven.springboottemplate.repository.HelloMessageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SayHelloService {

    @NonNull
    private final HelloMessageRepository helloMessageRepository;
    @NonNull
    private final MessageProperties messageProperties;
    @NonNull
    private final ApplicationEventPublisher applicationEventPublisher;


    @NonNull
    public HelloMessageDto createHelloMessage(@NonNull String name) {
        log.info("create HelloMessage");

        final HelloMessage messageToPersist = HelloMessage.builder()
                .created(new Date())
                .name(name)
                .build();
        helloMessageRepository.save(messageToPersist);

        applicationEventPublisher.publishEvent(new SyncEvent());

        return HelloMessageDto.builder()
                .name(name)
                .build();
    }

    @NonNull
    public List<HelloMessageDto> listHelloMessages() {
        log.info("list HelloMessages");

        return helloMessageRepository.findAll().stream()
                .limit(messageProperties.getMaxListItems())
                .map(entity -> HelloMessageDto.builder()
                        .name(entity.getName())
                        .created(entity.getCreated())
                        .build()
                )
                .toList();
    }

}
