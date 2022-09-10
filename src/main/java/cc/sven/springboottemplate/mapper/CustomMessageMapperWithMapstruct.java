package cc.sven.springboottemplate.mapper;

import cc.sven.springboottemplate.dto.CustomMessageDto;
import cc.sven.springboottemplate.entity.CustomMessage;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomMessageMapperWithMapstruct {

    CustomMessageMapperWithMapstruct INSTANCE = Mappers.getMapper(CustomMessageMapperWithMapstruct.class);

    //    @BeanMapping(ignoreByDefault = true)
//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "code", target = "code")
//    @Mapping(source = "sort", target = "sort")
//    @Mapping(source = "beschreibung", target = "beschreibung")
//    @Mapping(source = "schadteilClusterServiceId", target = "schadteilClusterServiceId")
//    @Mapping(source = "schadteilClusterServiceBeschreibung", target = "schadteilClusterServiceBeschreibung")
//    @Mapping(source = "schadteilClusterQualityId", target = "schadteilClusterQualityId")
//    @Mapping(source = "schadteilClusterQualityBeschreibung", target = "schadteilClusterQualityBeschreibung")
//    @Mapping(source = "PIRelevanz", target = "PIRelevanz")
//    @Mapping(source = "EPARelevanz", target = "EPARelevanz")
//    @Mapping(source = "masters", target = "masters")
    @NonNull
    CustomMessageDto toDto(@NonNull CustomMessage customMessage);


}
