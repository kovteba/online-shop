package kovteba.onlineshopcommon.pojo;


import kovteba.onlineshopcommon.enums.ProductType;
import lombok.*;

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

    private List<String> listPic;

    private Map<String, String> productInfo;

    private ProductType productType;

    public Product(String brand,
                   String model,
                   Long price,
                   String ean,
                   List<String> listPic,
                   Map<String, String> productInfo,
                   ProductType productType) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.ean = ean;
        this.listPic = listPic;
        this.productInfo = productInfo;
        this.productType = productType;
    }

}
