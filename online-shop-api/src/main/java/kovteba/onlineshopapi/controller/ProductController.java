package kovteba.onlineshopapi.controller;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.responce.ResponceProduct;
import kovteba.onlineshopapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductEntity> addNewProduct(@RequestHeader(value="Authorization") String token, @RequestBody ProductEntity productEntity) {
        log.info("addNewComputer, " + this.getClass());
        ResponceProduct responce = productService.addNewProduct(productEntity);
        return ResponseEntity.status(responce.getStatus()).body(responce.getObject());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@RequestHeader(value="Authorization") String token, @PathVariable Long id){
        log.info("getComouterById, " + this.getClass());
        ResponceProduct responce = productService.getProductById(id);
        return ResponseEntity.status(responce.getStatus()).body(responce.getObject());
    }

}
