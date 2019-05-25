package com.diving_fish.ebook.ServiceImpl;

import com.diving_fish.ebook.Entity.UserEntity;
import com.diving_fish.ebook.Repository.UserRepository;
import com.diving_fish.ebook.Service.UserManageService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserManageServiceImpl implements UserManageService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(String username, String password, String email, int role) {
        Random r = new Random();
        int r_id;
        while (true) {
            r_id = r.nextInt(90000000) + 10000000;
            UserEntity ue = userRepository.findById(r_id);
            if (ue == null) break;
        }
        UserEntity ue = new UserEntity(r_id, username, new BCryptPasswordEncoder().encode(password), email, role);
        userRepository.save(ue);
    }

    @Override
    public void createAdmin(String username, String password, String email) {
        createUser(username, password, email, 0);
    }

    @Override
    public void createCustomer(String username, String password, String email) {
        createUser(username, password, email, 1);
    }

    @Override
    public JSONObject getUserInfo(String username) {
        JSONObject jsonObject = new JSONObject();
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            jsonObject.put("status", "error");
            return jsonObject;
        }
        jsonObject.put("status", 200);
        jsonObject.put("id", user.getId());
        jsonObject.put("username", user.getUsername());
        jsonObject.put("role", user.getRole());
        jsonObject.put("enabled", user.isEnabled());
        return jsonObject;
    }

    @Override
    public boolean isLogin(String username) {
        return !getUserInfo(username).getString("status").equals("error");
    }

    @Override
    public boolean isUsernameValid(String username) {
        return (userRepository.findByUsername(username) == null);
    }
}
