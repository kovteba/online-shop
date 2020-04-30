package kovteba.onlineshopcommon.pojo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import kovteba.onlineshopcommon.enums.RoleUser;

import java.util.Map;

import kovteba.onlineshopcommon.model.RegistrationForm;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private RoleUser roleUser;

    private Map<Product, String> basket;

    public User installUser(RegistrationForm registrationForm, RoleUser roleUser){
        User user = new User();
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(registrationForm.getPassword());
        user.setPhoneNumber(registrationForm.getPhoneNumber());
        user.setRoleUser(roleUser);
        return user;
    }
}
