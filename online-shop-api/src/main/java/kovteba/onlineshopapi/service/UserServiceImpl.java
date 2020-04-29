package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopapi.repository.UserRepository;
import kovteba.onlineshopapi.responce.Responce;
import kovteba.onlineshopapi.util.BCryptUtil;
import kovteba.onlineshopcommon.enums.RoleUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductService productService;

    public UserServiceImpl(UserRepository userRepository,
                           ProductService productService) {
        this.userRepository = userRepository;
        this.productService = productService;
    }

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
            responce.setStatus(HttpStatus.BAD_REQUEST);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public Responce getUserByRole(RoleUser roleUser) {
        Responce responce = new Responce();
        List<UserEntity> userEntity = userRepository.findByRoleUser(roleUser);
        if (userEntity != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(userEntity);
        } else {
            responce.setStatus(HttpStatus.BAD_REQUEST);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public UserDetails authentication(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority(userEntity.getRoleUser().getRoleValue()));
        return new org.springframework.security.core.userdetails
                .User(userEntity.getEmail(), userEntity.getPassword(), roles);
    }

    @Override
    public Responce getUserByEmail(String email) {
        Responce responce = new Responce();
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) {
            responce.setStatus(HttpStatus.OK);
            responce.setObject(userEntity);
        } else {
            responce.setStatus(HttpStatus.BAD_REQUEST);
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
            responce.setStatus(HttpStatus.BAD_REQUEST);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public Responce addToBasketUser(Long id, Long productId, String count) {
        UserEntity userEntity = (UserEntity)getUserById(id).getObject();
        Map<ProductEntity, String> mapProduct = userEntity.getBasket();
        Responce responce = productService.getProductById(productId);
        mapProduct.put((ProductEntity) responce.getObject(), count);
        userEntity.setBasket(mapProduct);
        return new Responce(HttpStatus.CREATED, userRepository.save(userEntity));
    }

    @Override
    public Responce updateUserByEmail(String email, UserEntity userEntity) {
        Responce responce = new Responce();
        UserEntity userEntityDB = userRepository.findByEmail(email);
        if (userEntityDB != null){
            responce.setStatus(HttpStatus.OK);
            responce.setObject(userRepository.save(userEntity));
        } else {
            responce.setStatus(HttpStatus.BAD_REQUEST);
            responce.setObject(null);
        }
        return responce;
    }

    @Override
    public Responce deleteUserByEmail(String email) {
        Responce responce = new Responce();
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null){
            responce.setStatus(HttpStatus.BAD_REQUEST);
            responce.setObject(null);
        } else {
            if (userRepository.deleteByEmail(email) == null){
                responce.setStatus(HttpStatus.OK);
                responce.setObject(null);
            }
        }
        return responce;
    }

    @Override
    public boolean resetPass(String email, String newPass) {
        UserEntity userEntity = userRepository.findByEmail(email);
        userEntity.setPassword(BCryptUtil.encrypt(newPass));
        userRepository.save(userEntity);
        UserEntity testUser = userRepository.getById(userEntity.getId());
        return BCryptUtil.decrypt(newPass, testUser.getPassword());
    }

}
