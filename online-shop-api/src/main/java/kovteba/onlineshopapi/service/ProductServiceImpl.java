package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.repository.ProductRepository;
import kovteba.onlineshopapi.responce.Responce;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Responce addNewProduct(ProductEntity productEntity) {
        return new Responce(HttpStatus.CREATED, productRepository.save(productEntity));
    }

    @Override
    public Responce getProductById(Long id) {
        Responce responce = new Responce();
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

    @Override
    public Responce addInfoAboutProduct(Long id, String key, String value) {
        Responce responce = new Responce();
        ProductEntity productEntity = productRepository.getProductById(id);
        if (productEntity != null) {
            Map<String, String> infoList = productEntity.getProductInfo();
            infoList.put(key, value);
            productEntity.setProductInfo(infoList);
            responce.setStatus(HttpStatus.ACCEPTED);
            responce.setObject(productRepository.save(productEntity));
        } else {
            responce.setStatus(HttpStatus.BAD_REQUEST);
            responce.setObject("PRODUCT WITH " + id + " NOT FOUND");
        }
        return responce;
    }

    @Override
    public Responce addPicForProduct(Long id, String fileName) {
        Responce responce = new Responce();
        ProductEntity productEntity = productRepository.getProductById(id);
        if (productEntity != null) {
            List<String> picList = productEntity.getListPic();
            picList.add(fileName);
            productEntity.setListPic(picList);
            responce.setStatus(HttpStatus.ACCEPTED);
            responce.setObject(productRepository.save(productEntity));
        } else {
            responce.setStatus(HttpStatus.BAD_REQUEST);
            responce.setObject("PRODUCT WITH " + id + " NOT FOUND");
        }
        return responce;
    }

    @Override
    public Responce deletePicForProduct(Long id, String fileName) {
        Responce responce = new Responce();
        ProductEntity productEntity = productRepository.getProductById(id);
        if (productEntity != null) {
            List<String> picList = productEntity.getListPic();
            picList.remove(fileName);
            productEntity.setListPic(picList);
            responce.setStatus(HttpStatus.ACCEPTED);
            responce.setObject(productRepository.save(productEntity));
        } else {
            responce.setStatus(HttpStatus.BAD_REQUEST);
            responce.setObject("PRODUCT WITH " + id + " NOT FOUND");
        }
        return responce;
    }

}
