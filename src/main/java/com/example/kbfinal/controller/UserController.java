package com.example.kbfinal.controller;

import com.example.kbfinal.entity.Users;
import com.example.kbfinal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // user 정보를 입력, 삭제, 수정하는 API 생성
    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user) {
        /*
        try {
            userService.registerUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        */
        userService.registerUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/change/{id}")
    public String changePassword (@PathVariable Long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.checkIdAndChangePassword(id, oldPassword, newPassword);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable Long id) {
        /*
        try{
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
         */
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 전체 user List를 조회하는 api 생성
    @GetMapping("/userList")
    public ResponseEntity<List<Users>> getUserList() {
        /*
        try {
            List<Users> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
         */
        List<Users> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // 전체 user 의 숫자를 조회하는 api 생성
    @GetMapping("/userCount")
    public Long getUserCount() {
        /*
        try{
            return userService.countUsers();
        } catch (Exception e) {
            return null;
        }
         */
        return userService.countUsers();
    }
}
