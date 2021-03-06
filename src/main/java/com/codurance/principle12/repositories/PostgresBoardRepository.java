package com.codurance.principle12.repositories;

import com.codurance.principle12.entities.Board;
import com.codurance.principle12.entities.Card;
import com.codurance.principle12.entities.Column;
import com.codurance.principle12.repositories.mappers.BoardMapper;
import com.codurance.principle12.repositories.mappers.CardMapper;
import com.codurance.principle12.repositories.mappers.ColumnMapper;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PostgresBoardRepository implements BoardRepository {
  private final String INSERT_COLUMN = "insert into columns(title, board_id) values(?, ?)";
  private final String INSERT_BOARD = "insert into boards(title) values(?)";
  private final String SELECT_BOARD = "select * from boards where id = ?";
  private final String SELECT_COLUMNS = "select * from columns where board_id = ?";
  private final String SELECT_CARDS = "select * from cards where column_id = ? ORDER BY id ASC";
  private final String SELECT_USERS_BOARDS = "select boards.title, boards.id from boards inner join "
      + "users_boards on boards.id = users_boards.board_id where users_boards.user_id = ?";
  private final JdbcTemplate jdbcTemplate;

  public PostgresBoardRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public Board getBoard(Long id) {
    Board board = jdbcTemplate.queryForObject(SELECT_BOARD, new Object[]{id}, new BoardMapper());
    if (board != null) {
      List<Column> columns = jdbcTemplate.query(SELECT_COLUMNS, new Object[]{id}, new ColumnMapper());
      board.setColumns(columns);
      columns.forEach((this::getCards));
    }
    return board;
  }

  private void getCards(Column column) {
    List<Card> columnCards = jdbcTemplate.query(SELECT_CARDS, new Object[]{column.getId()}, new CardMapper());
    column.setCards(columnCards);
  }

  @Override
  public Board insert(Board board) {
    KeyHolder key = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      PreparedStatement statement = connection.prepareStatement(INSERT_BOARD, new String[]{"id"});
      statement.setString(1, board.getTitle());
      return statement;
    }, key);

    Long boardId = Objects.requireNonNull(key.getKey()).longValue();
    board.getColumns().forEach(c -> insertColumn(boardId, c));
    return getBoard(boardId);
  }


  private void insertColumn(Long boardId, Column column) {
    jdbcTemplate.update(connection -> {
      PreparedStatement statement = connection.prepareStatement(INSERT_COLUMN, new String[]{"id"});
      statement.setString(1, column.getTitle());
      statement.setLong(2, boardId);
      return statement;
    });
  }

  @Override
  public List<Board> getUsersBoards(Long userId) {
    return jdbcTemplate.query(SELECT_USERS_BOARDS, new Object[]{userId}, new BoardMapper());
  }
}
