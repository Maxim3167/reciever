package app.project.controller;

import app.project.entity.User;
import app.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final UserService userService;


    @PostMapping("/send")
    public ResponseEntity<?> recieveMessage(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findUserById(@RequestParam Long id) {
        return userService.findUserById(id);
    }

}
