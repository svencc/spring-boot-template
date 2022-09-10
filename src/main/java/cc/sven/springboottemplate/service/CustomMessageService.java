package cc.sven.springboottemplate.service;

import cc.sven.springboottemplate.dto.CustomMessageDto;
import cc.sven.springboottemplate.entity.CustomMessage;
import cc.sven.springboottemplate.mapper.CustomMessageMapperWithMapstruct;
import cc.sven.springboottemplate.mapper.SimpleCustomMessageMapper;
import cc.sven.springboottemplate.repository.CustomMessageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomMessageService {

    @NonNull
    private final CustomMessageRepository customMessageRepository;
    @NonNull
    private final SimpleCustomMessageMapper simpleCustomMessageMapper;

    @NonNull
    public CustomMessageDto createCustomMessage(@NonNull CustomMessageDto customMessage) {
        final CustomMessage messageToPersist = CustomMessage.builder()
                .recipient(customMessage.getRecipient())
                .created(new Date())
                .messageText(customMessage.getMessageText())
                .build();
        final CustomMessage persistedMessage = customMessageRepository.save(messageToPersist);

        // @TODO hier erlaubte recipient email-adressen aus application properties (Liste) prüfen (sven, emil; oder domänen erlauben), ansonsten folgende Exception werfen.
//        if (true) {
//            throw new HttpUnprocessableEntityException(); // Test des GlobalExceptionHandlers
//        }

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
