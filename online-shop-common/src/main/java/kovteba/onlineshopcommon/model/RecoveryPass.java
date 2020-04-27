package kovteba.onlineshopcommon.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryPass {

    private String email;

    private String secretToken;

    private String newPass;

}
