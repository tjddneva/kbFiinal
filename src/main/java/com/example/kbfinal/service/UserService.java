package com.example.kbfinal.service;

import com.example.kbfinal.entity.Users;
import com.example.kbfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TextEncryptor textEncryptor;

    public void registerUser(Users user) {
        // 비밀번호를 암호화하여 저장
        // password를 인코딩
        // user entity에 인코딩 된 password를 넣기

        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        //userRepository.save(user);

        user.setPassword(textEncryptor.encrypt(user.getPassword()));
        userRepository.save(user);
    }

   public boolean authenticate(String username, String password) {
       // 사용자 조회
       Users user = userRepository.findByUsername(username); // 직접 repo에서 구현
       if (user == null) {
           return false;
       }
       // 입력된 비밀번호와 저장된 암호화된 비밀번호를 비교
       return passwordEncoder.matches(password, user.getPassword());

   }

    // 이후 컨트롤러에서 들어오게 될  내용 추가 구현하기
    public String checkIdAndChangePassword(Long userId, String oldPassword, String newPassword) {
        // user의 credential 정보가 맞는지 확인
        if (checkPasswordByUserInfo(userId, oldPassword)) {
            if (changePassword(userId, newPassword)) {
                return "password has been changed successfully!";
            }
            else return "password not changed.";
        }
        else return "user is not exist.";
    }

    public Boolean changePassword(Long userId, String newPassword) {
        Users user = new Users();
        user.setId(userId);
        //user.setPassword(newPassword);
        user.setPassword(textEncryptor.encrypt(newPassword));
        userRepository.save(user);
        return true;
    }

    public Boolean checkPasswordByUserInfo(Long userId, String password) {
        Optional<Users> user = userRepository.findById(userId);

        //return user.filter(value -> passwordEncoder.matches(password, value.getPassword())).isPresent();

        if (textEncryptor.decrypt(user.get().getPassword()).equals(password)) {
            return true;
        }
        return false;
    }

    public void deleteUser(Long id) {
        /*
        if (!userRepository.existsById(id)) {
            throw new EmptyResultDataAccessException(1);
        }
         */
        userRepository.deleteById(id);
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Long countUsers() {
        return userRepository.countBy();
    }
}
