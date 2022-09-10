package cc.sven.springboottemplate.mapper;

import cc.sven.springboottemplate.dto.CustomMessageDto;
import cc.sven.springboottemplate.dto.CustomMessageDto.CustomMessageDtoBuilder;
import cc.sven.springboottemplate.entity.CustomMessage;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-10T18:11:07+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
public class CustomMessageMapperWithMapstructImpl implements CustomMessageMapperWithMapstruct {

    @Override
    public CustomMessageDto toDto(CustomMessage customMessage) {
        if ( customMessage == null ) {
            return null;
        }

        CustomMessageDtoBuilder customMessageDto = CustomMessageDto.builder();

        customMessageDto.recipient( customMessage.getRecipient() );
        customMessageDto.created( customMessage.getCreated() );
        customMessageDto.messageText( customMessage.getMessageText() );

        return customMessageDto.build();
    }
}
