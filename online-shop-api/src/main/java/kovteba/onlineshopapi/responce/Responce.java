package kovteba.onlineshopapi.responce;

import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopcommon.pojo.User;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Responce {

    private HttpStatus status;

    private Object object;

}
