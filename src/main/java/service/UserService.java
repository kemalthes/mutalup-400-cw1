package service;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import dto.UserDto;
import entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDao userDao = new UserDaoImpl();

    public List<UserDto> getAll() {
        List<User> list = userDao.getAll();
        List<UserDto> res = new ArrayList<>();
        for (User user : list) {
            res.add(new UserDto(user.getName(), user.getLastName(), user.getLogin()));
        }
        return res;
    }

    public void save(User user) {
        userDao.save(user);
    }

    public User getByLogin(String login) {
        Optional<User> wrap = userDao.getByLogin(login);
        return wrap.orElse(null);
    }
}
