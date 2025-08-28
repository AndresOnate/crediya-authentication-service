package co.com.pragma.api;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.UserUseCase;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    
    private final UserUseCase registerUserService;


    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(User.class)
                .flatMap(registerUserService::register)
                .flatMap(savedTask -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTask));
    }

}
