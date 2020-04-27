package kovteba.onlineshopapi.controller;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.mapper.ProductMapper;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.service.ProductService;
import kovteba.onlineshopcommon.model.ProductInfo;
import kovteba.onlineshopcommon.pojo.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${online.out.storage.img}")
    private String directory;

    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<Product> addNewProduct(@RequestHeader(value = "Authorization") String token, @RequestBody Product product) {
        log.info("addNewProduct, " + this.getClass());
        Responce responce = productService.addNewProduct(productMapper.productToProductEntity(product));
        return ResponseEntity.status(responce.getStatus()).body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
        log.info("getProductById, " + this.getClass());
        Responce responce = productService.getProductById(id);
        return ResponseEntity.status(responce.getStatus()).body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }

    @PostMapping("/addinfo/{idProduct}")
    public ResponseEntity<Product> addInfoAboutProduct(@PathVariable Long idProduct, @RequestBody ProductInfo productInfo){
        log.info("addInfoAboutProduct, " + this.getClass());
        Responce responce = productService.addInfoAboutProduct(idProduct, productInfo.getKey(), productInfo.getValue());
        return ResponseEntity.status(responce.getStatus()).body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }

    @PostMapping("/addpic/{idProduct}")
    public ResponseEntity<Product> addPicForProduct(@PathVariable Long idProduct, @RequestParam("file") MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(directory + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Responce responce = productService.addPicForProduct(idProduct, fileName);
        return ResponseEntity.status(responce.getStatus()).body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }

    @GetMapping("/deletepic/{idProduct}")
    public ResponseEntity<Product> deletePicForProduct(@PathVariable Long idProduct, @RequestBody String fileName){
        Responce responce = productService.deletePicForProduct(idProduct, fileName);
        return ResponseEntity.status(responce.getStatus()).body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }

    @GetMapping(
            value = "/pic/{idProduct}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPic(@PathVariable Long idProduct,HttpServletResponse response) throws IOException {
        ProductEntity productEntity = (ProductEntity) productService.getProductById(idProduct).getObject();
        List<String> listPic = productEntity.getListPic();
        List<File> pic = new ArrayList<>();
        for (String s : listPic){
            pic.add(new File(directory + s));
        }
        for (File file : pic){
            System.out.println(file.getAbsoluteFile());
            System.out.println(file.getName());
        }
//        InputStream[] targetStream = new InputStream[listPic.size()];
//        for (int i = 0; i < listPic.size(); i++) {
//            File file = new File(directory + listPic.get(i));
//            targetStream[i] = new DataInputStream(new FileInputStream(file));
//            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        }
//        for (InputStream inputStream : targetStream) {
//            IOUtils.copy(inputStream, response.getOutputStream());
//        }
    }
}
