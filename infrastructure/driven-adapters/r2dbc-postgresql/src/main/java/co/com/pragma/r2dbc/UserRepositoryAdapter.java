package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import reactor.core.publisher.Mono;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
    User,
    UserEntity,
    String,
    UserReactiveRepository
> {
    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
    }

    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email).map(userEntity -> mapper.map(userEntity, User.class));
    }


}
