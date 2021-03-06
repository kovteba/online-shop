package kovteba.onlineshopcommon.pojo;


import kovteba.onlineshopcommon.enums.ProductType;
import lombok.*;

import java.io.File;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;

    private String brand;

    private String model;

    private Long price;

    private String ean;

    private List<File> listPic;

    private Map<String, String> productInfo;

    private ProductType productType;

}
