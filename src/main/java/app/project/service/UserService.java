package app.project.service;

import app.project.entity.User;
import app.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<?> saveUser(User user) {
        if (!user.getEmail().contains("@")) {
            throw new RuntimeException();
        }
        User userWithHiddenPersonalData = hidePersonalData(user);
        User savedUser = userRepository.saveAndFlush(userWithHiddenPersonalData);
        if (userRepository.existsById(savedUser.getId())) {
            return ResponseEntity.ok().body(savedUser.getId());
        }
        return ResponseEntity.internalServerError().build();
    }

    private User hidePersonalData(User user) {
        String hiddenEmail;
        String hiddenPhone;
        String hiddenFio;
        String regex = "(?s)\\A.*\\z";
        if (user.getEmail() != null) {
            hiddenEmail = user.getEmail().replaceAll(regex, "***");
            user.setEmailHidden(hiddenEmail);
        }
        if (user.getPhone() != null) {
            hiddenPhone = user.getPhone().replaceAll(regex, "***");
            user.setPhoneHidden(hiddenPhone);
        }
        if (user.getEmail() != null) {
            hiddenFio = user.getFio().replaceAll(regex, "***");
            user.setFioHidden(hiddenFio);
        }
        return user;
    }
    @Transactional(readOnly = true)
    public ResponseEntity<?> findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.badRequest().body("Пользователь с заданным идентификатором не найден");
    }
}
