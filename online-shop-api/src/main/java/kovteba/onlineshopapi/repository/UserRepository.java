package kovteba.onlineshopapi.repository;


import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopcommon.enums.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByPhoneNumber(String phoneNumber);

    UserEntity findByRoleUser(RoleUser roleUser);

    UserEntity findByEmail(String email);

    UserEntity getById(Long id);

    UserEntity deleteByEmail(String email);


}
