package kovteba.onlineshopapi.mapper;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopcommon.pojo.Product;

public interface ProductMapper {

    Product productEntityToProduct(ProductEntity productEntity);

    ProductEntity productToProductEntity(Product product);

}
