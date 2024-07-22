package kanishka.firstwork.firstwork_app.service;

import kanishka.firstwork.firstwork_app.dto.UserDto;
import kanishka.firstwork.firstwork_app.model.Company;
import kanishka.firstwork.firstwork_app.model.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getUsersMoreThanAge(Integer ageLimit);

    List<User> getUsersBetweenAges(Integer ageLimit1, Integer ageLimit2);

    List<UserDto> getUsersLessThanEquals(Integer ageLimit);

    List<User> getUsersEqual(String fieldName, Integer age);

    List<UserDto> getAllUsers();

    List<User> getUserNameIn(List<String> names);

    User getUserByName(String name);
}
