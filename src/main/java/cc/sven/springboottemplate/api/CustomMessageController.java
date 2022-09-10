package cc.sven.springboottemplate.api;

import cc.sven.springboottemplate.dto.CustomMessageDto;
import cc.sven.springboottemplate.service.CustomMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "HelloWorld")
@RequestMapping("/api/v1")
public class CustomMessageController {

    @NonNull
    private CustomMessageService customMessageService;

    @Operation(
            summary = "Creates CustomMessage",
            description = "This endpoint creates CustomMessage"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PutMapping(value = "custom-message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomMessageDto> createCustomMessage(
            @Valid @RequestBody CustomMessageDto customMessage
    ) {
        log.info("Create CustomMessage");

        return ResponseEntity.ok(customMessageService.createCustomMessage(customMessage));
    }

    @Operation(
            summary = "List CustomMessage for recipient",
            description = "This endpoint creates CustomMessage"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "custom-message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomMessageDto>> createCustomMessage(
            @Parameter(description = "recipients name", required = true)
            @NonNull @RequestParam() String recipient
    ) {
        log.info("List CustomMessage for recipient {}", recipient);

        return ResponseEntity.ok(customMessageService.listMessagesForRecipient(recipient));
    }

}
