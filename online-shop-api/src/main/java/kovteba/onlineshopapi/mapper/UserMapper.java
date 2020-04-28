package kovteba.onlineshopapi.mapper;

import kovteba.onlineshopapi.entity.UserEntity;
import kovteba.onlineshopcommon.pojo.User;

public interface UserMapper {

    User userEntityToUser(UserEntity userEntity);

    UserEntity userToUserEntity(User user);

}
