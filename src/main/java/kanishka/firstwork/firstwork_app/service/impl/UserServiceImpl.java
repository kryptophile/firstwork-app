package kanishka.firstwork.firstwork_app.service.impl;

import kanishka.firstwork.firstwork_app.dto.UserDto;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.mapper.UserMapper;
import kanishka.firstwork.firstwork_app.model.Company;
import kanishka.firstwork.firstwork_app.model.User;
import kanishka.firstwork.firstwork_app.repository.UserRepository;
import kanishka.firstwork.firstwork_app.service.UserService;
import kanishka.firstwork.firstwork_app.utils.SpecificationUtils;
import kanishka.firstwork.firstwork_app.utils.UserSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class UserServiceImpl implements UserService {

    static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
         return UserMapper.mapToUserDto(userRepository.save(UserMapper.mapToUser(userDto)));
    }

    @Override
    public List<UserDto> getUsersMoreThanAge(Integer ageLimit) {
        List<UserDto> userList = new ArrayList<>();
        userRepository.findAll(SpecificationUtils.greaterThan("age", ageLimit))
                .forEach(x -> userList.add(UserMapper.mapToUserDto((User)x)));
        return userList;
    }

    @Override
    public List<User> getUsersBetweenAges(Integer ageLimit1, Integer ageLimit2) {
        return userRepository.findAll(where(UserSpecifications.ageMoreThan(ageLimit1)).
                and(UserSpecifications.ageLessThan(ageLimit2)));
    }

    @Override
    public List<UserDto> getUsersLessThanEquals(Integer ageLimit) {
        List<UserDto> userList = new ArrayList<>();
        Iterable<User> users = userRepository.findAll(where(UserSpecifications.ageMoreThan(ageLimit)).
                or(UserSpecifications.ageEquals(ageLimit)));

        for(User user : users){
                userList.add(UserMapper.mapToUserDto(user));
        }
        return userList;
    }

    @Override
    public List<User> getUsersEqual(String fieldName, Integer age) {
        return userRepository.findAll(UserSpecifications.ageMoreThan(age));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userList = new ArrayList<>();
        userRepository.findAll().forEach(x -> userList.add(UserMapper.mapToUserDto(x)));
        return userList;
    }

    @Override
    public List<User> getUserNameIn(List<String> names) {
        List<User> users = (List<User>) userRepository.findAll(SpecificationUtils
                        .in(DataFields.USER_NAME.value, names))
                .stream().collect(Collectors.toList());
        return users;
    }

    @Override
    public User getUserByName(String name) {
        Optional<User> user =
                userRepository.findAll(SpecificationUtils.equals(DataFields.USER_NAME.value, name)).stream().findFirst();

        if(user.isPresent())
            return user.get();
        logger.debug("No company found with name =" + name );
        return null;
    }

}
