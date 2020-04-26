package kovteba.onlineshopapi.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "secret_tokens")
public class RecoveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "secret_token")
    private String secreToken;

    public RecoveryEntity(String email, String secreToken) {
        this.email = email;
        this.secreToken = secreToken;
    }
}
