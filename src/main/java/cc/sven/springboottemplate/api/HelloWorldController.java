package cc.sven.springboottemplate.api;

import cc.sven.springboottemplate.dto.HelloMessageDto;
import cc.sven.springboottemplate.service.SayHelloService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "HelloWorld")
@RequestMapping("/api/v1")
public class HelloWorldController {

    @NonNull
    private SayHelloService sayHelloService;

    @Operation(
            summary = "Creates HelloMessage",
            description = "This endpoint creates HelloMessage"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PutMapping(value = "hello/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HelloMessageDto> createHelloMessage(
            @Parameter(required = true)
            @PathVariable
            @NonNull String name
    ) {
        log.info("Create HelloMessage for {}", name);

        return ResponseEntity.ok(sayHelloService.createHelloMessage(name));
    }

    @Operation(
            summary = "Lists HelloMessage",
            description = "This endpoint lists HelloMessage"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(value = "hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HelloMessageDto>> getHelloMessage() {
        log.info("List HelloMessages");

        return ResponseEntity.ok(sayHelloService.listHelloMessages());
    }

}
