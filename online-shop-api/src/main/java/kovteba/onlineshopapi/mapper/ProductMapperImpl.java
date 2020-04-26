package kovteba.onlineshopapi.mapper;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopcommon.pojo.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl implements ProductMapper{

    @Override
    public Product productEntityToProduct(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(product.getId());
        product.setBrand(productEntity.getBrand());
        product.setModel(productEntity.getModel());
        product.setPrice(productEntity.getPrice());
        product.setEan(productEntity.getEan());
        product.setListPic(productEntity.getListPic());
        product.setProductInfo(productEntity.getProductInfo());
        product.setProductType(productEntity.getProductType());
        return product;
    }

    @Override
    public ProductEntity productToProductEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setBrand(product.getBrand());
        productEntity.setModel(product.getModel());
        productEntity.setPrice(product.getPrice());
        productEntity.setEan(product.getEan());
        productEntity.setListPic(product.getListPic());
        productEntity.setProductInfo(product.getProductInfo());
        productEntity.setProductType(product.getProductType());
        return productEntity;
    }

}
