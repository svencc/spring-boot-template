package cc.sven.springboottemplate.dto;

import cc.sven.springboottemplate.util.MessageFormatUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HelloMessageDto {

    @JsonIgnore
    private String name;

    @Builder.Default
    @Schema(description = "Creation timestamp", example = "2022-09-08T14:00:36.817+00:00")
    private Date created = new Date();

    @JsonProperty(value = "message")
    @Schema(description = "Message description text.", example = "Hello <name>")
    public String getMessage() {
        return MessageFormatUtil.format(name, created);
    }

}
