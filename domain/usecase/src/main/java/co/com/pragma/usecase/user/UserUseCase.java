package co.com.pragma.usecase.user;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Pattern;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class UserUseCase {
    
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final UserRepository userRepository;

    /**
     * Registers a new user. Returns saved domain User with generated id.
     * This method is reactive and marked transactional to guarantee atomicity.
     */
    @Transactional
    public Mono<User> register(User draft) {
        log.trace("Start register user, email={}", draft.getEmail());

        return validate(draft)
            .then(userRepository.findByEmail(draft.getEmail()))
            .flatMap(exists -> {
                if (exists) {
                    log.warn("Attempt to register already existing email={}", draft.getEmail());
                    return Mono.error(new DuplicateKeyException("Email already registered"));
                }
                User toSave = User.builder()
                        .id(UUID.randomUUID().toString())
                        .firstName(draft.getFirstName())
                        .lastName(draft.getLastName())
                        .birthDate(draft.getBirthDate())
                        .address(draft.getAddress())
                        .phone(draft.getPhone())
                        .email(draft.getEmail())
                        .baseSalary(draft.getBaseSalary())
                        .build();

                return userRepository.save(toSave)
                        .doOnSuccess(u -> log.info("User created id={}, email={}", u.getId(), u.getEmail()));
            });
    }

    private Mono<Void> validate(User d) {
        if (isBlank(d.getFirstName())) {
            return Mono.error(new ValidationException("Field 'firstName' is required"));
        }
        if (isBlank(d.getLastName())) {
            return Mono.error(new ValidationException("Field 'lastName' is required"));
        }
        if (isBlank(d.getEmail())) {
            return Mono.error(new ValidationException("Field 'email' is required"));
        }
        if (!EMAIL_PATTERN.matcher(d.getEmail()).matches()) {
            return Mono.error(new ValidationException("Invalid email format"));
        }
        if (d.getBaseSalary() == null) {
            return Mono.error(new ValidationException("Field 'baseSalary' is required"));
        }
        BigDecimal min = BigDecimal.ZERO;
        BigDecimal max = new BigDecimal("15000000");
        if (d.getBaseSalary().compareTo(min) < 0 || d.getBaseSalary().compareTo(max) > 0) {
            return Mono.error(new ValidationException("Field 'baseSalary' must be between 0 and 15,000,000"));
        }
        return Mono.empty();
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }


}
