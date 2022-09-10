package cc.sven.springboottemplate.mapper;


import cc.sven.springboottemplate.dto.CustomMessageDto;
import cc.sven.springboottemplate.entity.CustomMessage;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SimpleCustomMessageMapper {

    @NonNull
    public CustomMessageDto map(@NonNull CustomMessage entity) {
        return CustomMessageDto.builder()
                .recipient(entity.getRecipient())
                .messageText(entity.getMessageText())
                .created(entity.getCreated())
                .build();
    }

}
