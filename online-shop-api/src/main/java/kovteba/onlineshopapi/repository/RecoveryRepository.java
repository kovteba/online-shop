package kovteba.onlineshopapi.repository;

import kovteba.onlineshopapi.entity.Recovery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecoveryRepository extends JpaRepository<Recovery, Long> {

    Recovery getByEmail(String email);

}
