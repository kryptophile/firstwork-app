package kanishka.firstwork.firstwork_app.mapper;

import kanishka.firstwork.firstwork_app.dto.UserDto;
import kanishka.firstwork.firstwork_app.model.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto){
        return new User(
                userDto.getName(),
                userDto.getAge(),
                userDto.getCountry()
        );
    }

    public static UserDto mapToUserDto(User user){
        return new UserDto(
                user.getName(),
                user.getAge(),
                user.getCountry()
        );
    }
}
