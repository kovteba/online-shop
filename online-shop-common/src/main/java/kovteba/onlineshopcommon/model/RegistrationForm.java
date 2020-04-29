package kovteba.onlineshopcommon.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

}
