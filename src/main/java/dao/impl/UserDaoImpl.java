package dao.impl;

import dao.UserDao;
import entity.User;
import util.DataBaseConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final Connection connection = DataBaseConnectionUtil.getConnection();

    @Override
    public List<User> getAll() {
        //language=sql
        String sql = "select * from users";
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                ));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        //language=sql
        String sql = "insert into users (name, lastname, login, password) values (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalArgumentException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getByLogin(String login) {
        //language=sql
        String sql = "select * from users where login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getString("login"),
                        resultSet.getString("password")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
