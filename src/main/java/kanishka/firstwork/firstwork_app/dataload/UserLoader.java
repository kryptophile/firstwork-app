package kanishka.firstwork.firstwork_app.dataload;

import kanishka.firstwork.firstwork_app.dto.UserDto;
import kanishka.firstwork.firstwork_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Order(1)
@Component
public class UserLoader implements ApplicationRunner {

    private final UserService userService;

    @Autowired
    public UserLoader(final UserService userService) {
        this.userService = userService;
    }

    public void run(final ApplicationArguments args) {

        List<UserDto> userList = new ArrayList<>();
        userList.add(new UserDto("User1", 20, "India"));
        userList.add(new UserDto("User2", 22, "Russia"));
        userList.add(new UserDto("User3", 25, "India"));
        userList.add(new UserDto("User4", 26, "US"));
        userList.add(new UserDto("User4", 29, "Russia"));
        userList.add(new UserDto("User5", 30, "India"));
        userList.add(new UserDto("User6", 32, "US"));
        userList.add(new UserDto("User7", 35, "US"));
        userList.add(new UserDto("User8", 40, "US"));
        userList.add(new UserDto("User9", 42, "India"));
        userList.add(new UserDto("User10", 45, "Russia"));

        for(UserDto userDto : userList){
            userService.createUser(userDto);
        }
    }
}
