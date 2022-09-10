package cc.sven.springboottemplate.service;

import cc.sven.springboottemplate.dto.HelloMessageDto;
import cc.sven.springboottemplate.entity.HelloMessage;
import cc.sven.springboottemplate.property.HelloProperties;
import cc.sven.springboottemplate.repository.HelloMessageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final HelloProperties helloProperties;


    @NonNull
    public HelloMessageDto createHelloMessage(@NonNull String name) {
        log.info("create HelloMessage");

        final HelloMessage messageToPersist = HelloMessage.builder()
                .created(new Date())
                .name(name)
                .build();
        helloMessageRepository.save(messageToPersist);

        return HelloMessageDto.builder()
                .name(name)
                .build();
    }

    @NonNull
    public List<HelloMessageDto> listHelloMessages() {
        log.info("list HelloMessages");

        return helloMessageRepository.findAll().stream()
                .limit(helloProperties.getMaxListItems())
                .map(entity -> HelloMessageDto.builder()
                        .name(entity.getName())
                        .created(entity.getCreated())
                        .build()
                )
                .toList();
    }

}
