package com.codurance.principle12.repositories;

import com.codurance.principle12.entities.User;
import com.codurance.principle12.exceptions.UserNotFoundException;
import com.codurance.principle12.repositories.mappers.UserMapper;
import java.sql.PreparedStatement;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresUserRepository implements UserRepository{
  private final String SELECT_USER_BY_EMAIL = "select * from users where email = ?";
  private final String SELECT_USER_BY_ID = "select * from users where id = ?";
  private final String INSERT_USER = "insert into users (email,username) values (?,?)";
  private final String INSERT_USER_TO_BOARD = "insert into users_boards (user_id,board_id) values (?,?)";
  private final String SELECT_USER_FROM_BOARD = "SELECT EXISTS(SELECT FROM users_boards WHERE user_id = ? and board_id = ?)";

  private final JdbcTemplate jdbcTemplate;

  public PostgresUserRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public User findByEmail(String email) {
    try {
      return jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL, new UserMapper(), email);
    } catch (RuntimeException exception) {
      throw new UserNotFoundException();
    }
  }

  @Override
  public User register(User user) {
    KeyHolder key = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement statement = connection.prepareStatement(INSERT_USER, new String[]{"id"});
      statement.setString(1, user.email);
      statement.setString(2, user.username);
      return statement;
    }, key);

    Long id = Objects.requireNonNull(key.getKey()).longValue();
    return findById(id);
  }

  @Override
  public void addToBoard(Long userId, Long boardId) {
    Boolean userExistsOnBoard = jdbcTemplate.queryForObject(SELECT_USER_FROM_BOARD, Boolean.class, userId, boardId);
    if (userExistsOnBoard != null && !userExistsOnBoard) {
      jdbcTemplate.update(INSERT_USER_TO_BOARD, userId, boardId);
    }
  }

  public User findById(Long id) {
    try {
      return jdbcTemplate.queryForObject(SELECT_USER_BY_ID, new UserMapper(), id);
    } catch (RuntimeException exception) {
      throw new UserNotFoundException();
    }
  }
}
