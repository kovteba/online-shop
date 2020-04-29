package kovteba.onlineshopapi.mapper;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopcommon.pojo.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductMapperImpl implements ProductMapper {

    @Value("${online.out.storage.img}")
    private String directory;

    @Override
    public Product productEntityToProduct(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getId());
        product.setBrand(productEntity.getBrand());
        product.setModel(productEntity.getModel());
        product.setPrice(productEntity.getPrice());
        product.setEan(productEntity.getEan());
        List<String> picNameList = productEntity.getListPic();
        List<File> listFilePic = new ArrayList<>();
        for (String fileName : picNameList) {
            listFilePic.add(new File(directory + fileName));
        }
        product.setListPic(listFilePic);
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
        List<String> picNameList = new ArrayList<>();
        List<File> listFilePic = product.getListPic();
        if (listFilePic.size() != 0){
            for (File file : listFilePic){
                picNameList.add(file.getName());
            }
        }
        productEntity.setListPic(picNameList);
        productEntity.setProductInfo(product.getProductInfo());
        productEntity.setProductType(product.getProductType());
        return productEntity;
    }

}
