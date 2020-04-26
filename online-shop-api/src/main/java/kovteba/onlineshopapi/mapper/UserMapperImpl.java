package kovteba.onlineshopapi.mapper;

import kovteba.onlineshopapi.entity.ProductEntity;
import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopcommon.pojo.Product;
import kovteba.onlineshopcommon.pojo.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {

    @Autowired
    private final ProductMapper productMapper;

    @Override
    public User userEntityToUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setPassword(userEntity.getPassword());
        Map<Product, String> basket = new HashMap<>();
        for (Map.Entry<ProductEntity, String> entry : userEntity.getBasket().entrySet()){
            basket.put(productMapper.productEntityToProduct(entry.getKey()), entry.getValue());
        }
        user.setBasket(basket);
        user.setRoleUser(userEntity.getRoleUser());
        return user;
    }

    @Override
    public UserEntity userToUserEntity(User user) {
        return null;
    }
}