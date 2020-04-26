package kovteba.onlineshopapi.repository;

import kovteba.onlineshopapi.entity.RecoveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecoveryRepository extends JpaRepository<RecoveryEntity, Long> {

    RecoveryEntity getByEmail(String email);

}
