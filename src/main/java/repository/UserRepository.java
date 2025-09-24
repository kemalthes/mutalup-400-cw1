package repository;

import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {

    private final static Map<String, User> data = new HashMap<>();

    public Optional<User> getUser(String name) {
        if (data.containsKey(name)) {
            return Optional.of(data.get(name));
        }
        return Optional.empty();
    }

    public void setUser(String name, String password) {
        data.put(name, new User(name, password));
    }

}
