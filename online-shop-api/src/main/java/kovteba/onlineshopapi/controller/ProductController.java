package kovteba.onlineshopapi.controller;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.mapper.ProductMapper;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.service.ProductService;
import kovteba.onlineshopcommon.model.ProductInfo;
import kovteba.onlineshopcommon.pojo.Product;
import org.apache.commons.io.IOUtils;
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

    @PostMapping("/addNewProduct")
    public ResponseEntity<Product> addNewProduct(@RequestHeader(value = "Authorization") String token,
                                                 @RequestBody Product product) {
        log.info("addNewProduct, " + this.getClass());
        Responce responce = productService.addNewProduct(productMapper.productToProductEntity(product));
        return ResponseEntity.status(responce.getStatus())
                .body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@RequestHeader(value = "Authorization") String token,
                                                  @PathVariable Long id) {
        log.info("getProductById, " + this.getClass());
        Responce responce = productService.getProductById(id);
        return ResponseEntity.status(responce.getStatus())
                .body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }

    @PostMapping("/addInfo/{idProduct}")
    public ResponseEntity<Product> addInfoAboutProduct(@RequestHeader(value = "Authorization") String token,
                                                       @PathVariable Long idProduct,
                                                       @RequestBody ProductInfo productInfo) {
        log.info("addInfoAboutProduct, " + this.getClass());
        Responce responce = productService.addInfoAboutProduct(idProduct, productInfo.getKey(), productInfo.getValue());
        return ResponseEntity.status(responce.getStatus())
                .body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }

    @PostMapping("/addPic/{idProduct}")
    public ResponseEntity<Product> addPicForProduct(@RequestHeader(value = "Authorization") String token,
                                                    @PathVariable Long idProduct,
                                                    @RequestParam("file") MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(directory + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Responce responce = productService.addPicForProduct(idProduct, fileName);
        return ResponseEntity.status(responce.getStatus())
                .body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }

    @GetMapping("/deletePic/{idProduct}")
    public ResponseEntity<Product> deletePicForProduct(@RequestHeader(value = "Authorization") String token,
                                                       @PathVariable Long idProduct,
                                                       @RequestBody String fileName) {
        Responce responce = productService.deletePicForProduct(idProduct, fileName);
        return ResponseEntity.status(responce.getStatus())
                .body(productMapper.productEntityToProduct((ProductEntity) responce.getObject()));
    }


    ///////////////////////
    @GetMapping(
            value = "/pic/{idProduct}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPic(@RequestHeader(value = "Authorization") String token,
                       @PathVariable Long idProduct,
                       HttpServletResponse response) throws IOException {

        ProductEntity productEntity = (ProductEntity) productService.getProductById(idProduct).getObject();
//        File file = new File(directory + "IMG_1050.JPEG");
//        InputStream targetStream = new DataInputStream(new FileInputStream(file));
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        IOUtils.copy(targetStream, response.getOutputStream());

        List<String> listPic = productEntity.getListPic();
        List<File> pic = new ArrayList<>();
        for (String s : listPic) {
            pic.add(new File(directory + s));
        }

        InputStream[] targetStream = new InputStream[listPic.size()];
        for (int i = 0; i < listPic.size(); i++) {
            File file = new File(directory + listPic.get(i));
            targetStream[i] = new DataInputStream(new FileInputStream(file));
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        }
        for (InputStream inputStream : targetStream) {
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }
}
