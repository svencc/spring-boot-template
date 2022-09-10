package cc.sven.springboottemplate.service;

import cc.sven.springboottemplate.dto.CustomMessageDto;
import cc.sven.springboottemplate.entity.CustomMessage;
import cc.sven.springboottemplate.event.event.AsyncEvent;
import cc.sven.springboottemplate.exception.HttpUnprocessableEntityException;
import cc.sven.springboottemplate.mapper.CustomMessageMapperWithMapstruct;
import cc.sven.springboottemplate.property.MessageProperties;
import cc.sven.springboottemplate.repository.CustomMessageRepository;
import cc.sven.springboottemplate.util.MessageFormatUtil;
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
public class CustomMessageService {

    @NonNull
    private final MessageProperties messageProperties;
    @NonNull
    private final CustomMessageRepository customMessageRepository;
    // @NonNull
    // private final SimpleCustomMessageMapper simpleCustomMessageMapper;
    @NonNull
    private final ApplicationEventPublisher applicationEventPublisher;

    @NonNull
    public CustomMessageDto createCustomMessage(@NonNull CustomMessageDto customMessage) {
        final CustomMessage messageToPersist = CustomMessage.builder()
                .recipient(customMessage.getRecipient())
                .created(new Date())
                .messageText(customMessage.getMessageText())
                .build();
        final CustomMessage persistedMessage = customMessageRepository.save(messageToPersist);

        if (!messageProperties.getValidDomains().contains(MessageFormatUtil.parseDomain(customMessage.getRecipient()))) {
            throw new HttpUnprocessableEntityException(); // Test des GlobalExceptionHandlers
        }

        applicationEventPublisher.publishEvent(new AsyncEvent());

//        return customMessageMapper.map(persistedMessage); // Das ist ein einfacher mapper der auch ok ist.
        return CustomMessageMapperWithMapstruct.INSTANCE.toDto(persistedMessage); // Mapper mit mapstruct. Sehr mächtig.
    }


    @NonNull
    public List<CustomMessageDto> listMessagesForRecipient(@NonNull String recipient) {
        return customMessageRepository.findAllByRecipientWithNativeQuery(recipient).stream()
                .map(CustomMessageMapperWithMapstruct.INSTANCE::toDto)
                .toList();
    }

}
