package co.com.pragma.r2dbc;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import co.com.pragma.r2dbc.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, String>, ReactiveQueryByExampleExecutor<UserEntity> {
    Mono<UserEntity> findByEmail(String email);
}
