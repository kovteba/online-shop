package kovteba.onlineshopcommon.pojo;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Recovery {

    private Long id;

    private String email;

    private String secreToken;

    public Recovery(String email, String secreToken) {
        this.email = email;
        this.secreToken = secreToken;
    }
}
