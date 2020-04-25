package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.Product;
import kovteba.onlineshopapi.entity.User;
import kovteba.onlineshopapi.enums.RoleUser;
import kovteba.onlineshopapi.repository.UserRepository;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.responce.ResponceProduct;
import kovteba.onlineshopapi.util.BCryptUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ProductService productService;

    @Override
    public Responce addNewUser(User user) {
        user.setPassword(BCryptUtil.encrypt(user.getPassword()));
        return new Responce(HttpStatus.CREATED, userRepository.save(user));
    }

    @Override
    public Responce getUserByPhoneNumber(String phoneNUmber) {
        Responce responce = new Responce();
        User user = userRepository.findByPhoneNumber(phoneNUmber);
        if (user != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(user);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public Responce getUserByRole(RoleUser roleUser) {
        Responce responce = new Responce();
        User user = userRepository.findByRoleUser(roleUser);
        if (user != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(user);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public UserDetails authentication(String email) {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public Responce getUserByEmail(String email) {
        Responce responce = new Responce();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(user);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public Responce getUserById(Long id) {
        Responce responce = new Responce();
        User user = userRepository.getById(id);
        if (user != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(user);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public Responce addToBasketUser(Long id, Long productId, String count) {
        User user = (User)getUserById(id).getObject();
        Map<Product, String> mapProduct = user.getBasket();
        ResponceProduct responce = productService.getProductById(productId);
        mapProduct.put((Product) responce.getObject(), count);
        user.setBasket(mapProduct);
        return new Responce(HttpStatus.CREATED, userRepository.save(user));
    }

}
