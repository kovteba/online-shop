package kovteba.onlineshopapi.service;


import kovteba.onlineshopapi.entity.Product;
import kovteba.onlineshopapi.responce.ResponceProduct;

public interface ProductService {

    ResponceProduct addNewProduct(Product product);

    ResponceProduct getProductById(Long id);

}
