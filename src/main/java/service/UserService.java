package service;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import dto.UserDto;
import entity.User;
import util.PasswordUtil;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDao userDao = new UserDaoImpl();

    public List<UserDto> getAll() {
        List<User> list = userDao.getAll();
        List<UserDto> res = new ArrayList<>();
        for (User user : list) {
            res.add(new UserDto(user.getName(), user.getLastName(), user.getLogin(), user.getImage()));
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

    public String saveImage(Part part) throws IOException {
        final String FILE_PREFIX = "D:\\Java\\Орис\\HttpSevlet\\src\\main\\webapp\\images";
        final int DIR_COUNT = 100;
        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        int hash = Math.abs(filename.hashCode() % DIR_COUNT);
        String path = FILE_PREFIX + File.separator + hash + File.separator + filename;
        File file = new File(path);
        InputStream content = part.getInputStream();
        if (file.getParentFile().mkdirs() &&
                file.createNewFile()) {
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[content.available()];
            content.read(buffer);
            outputStream.write(buffer);
            outputStream.close();
        }
        return "images/" + hash + "/" + filename;
    }
}

