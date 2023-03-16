package shop.mtcoding.hiberapp.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.hiberapp.model.User;
import shop.mtcoding.hiberapp.model.UserJpaRepository;
import shop.mtcoding.hiberapp.model.UserRepository;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    // private final UserRepository userRepository;
    private final UserJpaRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(User user) {
        User userPS = userRepository.save(user);
        return new ResponseEntity<>(userPS, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, User user) {
        // 신뢰성 검사
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }
        userPS.update(user.getPassword(), user.getEmail());
        User updateuserPS = userRepository.save(userPS);
        return new ResponseEntity<>(updateuserPS, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, User user) {
        // 신뢰성 검사
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(userPS);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findUsers(@RequestParam(defaultValue = "0") int page) {
        Page<User> userListPS = userRepository.findAll(PageRequest.of(page, 2));
        return new ResponseEntity<>(userListPS, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserOne(@PathVariable Long id) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }
}
