package kovteba.onlineshopapi.responce;

import kovteba.onlineshopapi.entity.Product;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponceProduct {

    private HttpStatus status;

    private Product object;

}
