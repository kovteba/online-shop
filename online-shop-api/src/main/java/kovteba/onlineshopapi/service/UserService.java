package kovteba.onlineshopapi.service;


import kovteba.onlineshopapi.entity.User;
import kovteba.onlineshopapi.enums.RoleUser;
import kovteba.onlineshopapi.responce.Responce;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    Responce addNewUser(User user);

    Responce getUserByPhoneNumber(String phoneNUmber);

    Responce getUserByRole(RoleUser roleUser);

    UserDetails authentication(String email);

    Responce getUserByEmail(String email);

    Responce getUserById(Long id);

    Responce addToBasketUser(Long id, Long computerId, String count);

}
