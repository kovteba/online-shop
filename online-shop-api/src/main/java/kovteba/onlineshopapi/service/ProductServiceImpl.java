package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.repository.ProductRepository;
import kovteba.onlineshopapi.responce.ResponceProduct;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ResponceProduct addNewProduct(ProductEntity productEntity) {
        return new ResponceProduct(HttpStatus.CREATED, productRepository.save(productEntity));
    }

    @Override
    public ResponceProduct getProductById(Long id) {
        ResponceProduct responce = new ResponceProduct();
        ProductEntity productEntity = productRepository.getProductById(id);
        if (productEntity != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(productEntity);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

}
