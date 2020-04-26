package kovteba.onlineshopapi.entity;

import kovteba.onlineshopcommon.enums.RoleUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    private RoleUser roleUser;

    @ElementCollection
    @CollectionTable(name = "user_basket")
    private Map<ProductEntity, String> basket;

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String email, String phoneNumber, String password, Map<ProductEntity, String> basket) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.basket = basket;
    }

}
