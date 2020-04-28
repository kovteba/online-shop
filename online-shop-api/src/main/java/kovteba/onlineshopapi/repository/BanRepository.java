package kovteba.onlineshopapi.repository;

import kovteba.onlineshopapi.entity.BanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanRepository extends JpaRepository<BanEntity, Long> {

    BanEntity findByEmail(String email);

}
