package kovteba.onlineshopapi.repository;


import kovteba.onlineshopapi.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity getProductById(Long id);

}
