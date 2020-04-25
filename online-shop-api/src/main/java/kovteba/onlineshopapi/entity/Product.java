package kovteba.onlineshopapi.entity;

import kovteba.onlineshopapi.enums.ProductType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "ean", nullable = false)
    private String ean;

    @ElementCollection
    @CollectionTable(name = "product_pic_list")
    private List<String> listPic;

    @ElementCollection
    @CollectionTable(name = "product_info_list")
    private Map<String, String> productInfo;

    @Column(name = "type", nullable = false)
    private ProductType productType;

    public Product() {
    }

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
