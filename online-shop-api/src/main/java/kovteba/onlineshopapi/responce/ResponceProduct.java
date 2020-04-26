package kovteba.onlineshopapi.responce;

import kovteba.onlineshopapi.entity.ProductEntity;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponceProduct {

    private HttpStatus status;

    private ProductEntity object;

}
