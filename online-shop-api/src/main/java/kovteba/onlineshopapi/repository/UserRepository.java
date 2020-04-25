package kovteba.onlineshopapi.repository;


import kovteba.onlineshopapi.entity.User;
import kovteba.onlineshopapi.enums.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhoneNumber(String phoneNumber);

    User findByRoleUser(RoleUser roleUser);

    User findByEmail(String email);

    User getById(Long id);


}
