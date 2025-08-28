package co.com.pragma.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal baseSalary;
}