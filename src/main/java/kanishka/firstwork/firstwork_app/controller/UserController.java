package kanishka.firstwork.firstwork_app.controller;

import kanishka.firstwork.firstwork_app.dto.UserDto;
import kanishka.firstwork.firstwork_app.enumeration.DataFields;
import kanishka.firstwork.firstwork_app.service.UserService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/getUsersMoreThanAge")
    public ResponseEntity<List<UserDto>> getUsersMoreThanAge(@RequestBody Map<String, Integer> request) {
        return new ResponseEntity<>(userService.getUsersMoreThanAge(request.get(DataFields.USER_AGE.value)), HttpStatus.OK);
    }
}
