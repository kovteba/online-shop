package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.Product;
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
    public ResponceProduct addNewProduct(Product product) {
        return new ResponceProduct(HttpStatus.CREATED, productRepository.save(product));
    }

    @Override
    public ResponceProduct getProductById(Long id) {
        ResponceProduct responce = new ResponceProduct();
        Product product = productRepository.getProductById(id);
        if (product != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(product);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

}
