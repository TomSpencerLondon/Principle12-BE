package com.codurance.principle12.repositories.mappers;

import com.codurance.principle12.entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet resultSet, int i) throws SQLException {
    return new User(
        resultSet.getLong("id"),
        resultSet.getString("email"),
        resultSet.getString("username")
    );
  }
}
