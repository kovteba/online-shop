package kovteba.onlineshopapi.responce;

import kovteba.onlineshopapi.util.ResponceType;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Responce {

    private HttpStatus status;

    private ResponceType object;

}
