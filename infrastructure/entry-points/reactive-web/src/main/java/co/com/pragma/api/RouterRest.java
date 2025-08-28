package co.com.pragma.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final Handler apiHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/users"), apiHandler::listenGETUseCase);
    }

}
