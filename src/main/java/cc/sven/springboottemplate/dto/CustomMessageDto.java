package cc.sven.springboottemplate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Schema
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomMessageDto {

    @Email
    @JsonProperty
    @Schema(description = "Name of recipient", example = "Hugo@email.com")
    private String recipient;

    @JsonProperty
    @Schema(description = "Creation timestamp", example = "2022-09-08T14:00:36.817+00:00")
    private Date created;

//    @NotBlank
    @Size(min = 5, max = 65)
//    @Pattern(regexp = "^(\\d{2}$)|(^\\d{4})$", message = "meine eigene Message")
    @JsonProperty
    @Schema(description = "Message text.", example = "Hello <name>. further wall of text")
    private String messageText;

}
