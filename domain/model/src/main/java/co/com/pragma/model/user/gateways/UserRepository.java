package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<Boolean> findByEmail(String email);
    Mono<User> save(User user);
}
