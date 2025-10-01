package repository;

import dto.UserDto;
import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class UserRepository {

    private final static Map<String, User> data = new HashMap<>();

    public List<UserDto> getAll() {
        return data.values()
                .stream()
                .map(c -> new UserDto(c.name(), c.score()))
                .toList();
    }

    public Optional<User> getByName(String name) {
        if (data.containsKey(name)) {
            return Optional.of(data.get(name));
        }
        return Optional.empty();
    }

    public void save(String name, String password) {
        data.put(name, new User(password, name, new Random().nextInt(100)));
    }

}
