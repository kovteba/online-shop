package kovteba.onlineshopapi.service;


import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.responce.Responce;

public interface ProductService {

    Responce addNewProduct(ProductEntity productEntity);

    Responce getProductById(Long id);

    Responce addInfoAboutProduct(Long id, String key, String value);

    Responce addPicForProduct(Long id, String fileName);

    Responce deletePicForProduct(Long id, String fileName);

}
