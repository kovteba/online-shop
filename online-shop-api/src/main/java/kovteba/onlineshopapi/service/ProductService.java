package kovteba.onlineshopapi.service;


import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.responce.ResponceProduct;

public interface ProductService {

    ResponceProduct addNewProduct(ProductEntity productEntity);

    ResponceProduct getProductById(Long id);

}
