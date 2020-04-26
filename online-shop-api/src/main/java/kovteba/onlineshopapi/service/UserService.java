package kovteba.onlineshopapi.service;


import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopcommon.enums.RoleUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    Responce addNewUser(UserEntity userEntity);

    Responce getUserByPhoneNumber(String phoneNUmber);

    Responce getUserByRole(RoleUser roleUser);

    UserDetails authentication(String email);

    Responce getUserByEmail(String email);

    Responce getUserById(Long id);

    Responce addToBasketUser(Long id, Long computerId, String count);

}
