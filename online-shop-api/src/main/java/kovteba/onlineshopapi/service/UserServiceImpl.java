package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopapi.repository.UserRepository;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.responce.ResponceProduct;
import kovteba.onlineshopapi.util.BCryptUtil;
import kovteba.onlineshopcommon.enums.RoleUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ProductService productService;

    @Override
    public Responce addNewUser(UserEntity userEntity) {
        userEntity.setPassword(BCryptUtil.encrypt(userEntity.getPassword()));
        return new Responce(HttpStatus.CREATED, userRepository.save(userEntity));
    }

    @Override
    public Responce getUserByPhoneNumber(String phoneNUmber) {
        Responce responce = new Responce();
        UserEntity userEntity = userRepository.findByPhoneNumber(phoneNUmber);
        if (userEntity != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(userEntity);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public Responce getUserByRole(RoleUser roleUser) {
        Responce responce = new Responce();
        UserEntity userEntity = userRepository.findByRoleUser(roleUser);
        if (userEntity != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(userEntity);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public UserDetails authentication(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails
                .User(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
    }

    @Override
    public Responce getUserByEmail(String email) {
        Responce responce = new Responce();
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(userEntity);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public Responce getUserById(Long id) {
        Responce responce = new Responce();
        UserEntity userEntity = userRepository.getById(id);
        if (userEntity != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(userEntity);
        } else {
            responce.setStatus(HttpStatus.NO_CONTENT);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public Responce addToBasketUser(Long id, Long productId, String count) {
        UserEntity userEntity = (UserEntity)getUserById(id).getObject();
        Map<ProductEntity, String> mapProduct = userEntity.getBasket();
        ResponceProduct responce = productService.getProductById(productId);
        mapProduct.put((ProductEntity) responce.getObject(), count);
        userEntity.setBasket(mapProduct);
        return new Responce(HttpStatus.CREATED, userRepository.save(userEntity));
    }

}
