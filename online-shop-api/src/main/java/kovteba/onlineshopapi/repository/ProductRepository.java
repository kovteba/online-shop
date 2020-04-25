package kovteba.onlineshopapi.repository;


import kovteba.onlineshopapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(Long id);

}
