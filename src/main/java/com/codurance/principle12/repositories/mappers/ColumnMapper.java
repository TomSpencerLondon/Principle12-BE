package com.codurance.principle12.repositories.mappers;

import com.codurance.principle12.entities.Column;
import com.codurance.principle12.entities.ColumnType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ColumnMapper implements RowMapper<Column> {

  @Override
  public Column mapRow(ResultSet resultSet, int i) throws SQLException {
    return new Column(
        resultSet.getLong("id"),
        ColumnType.of(resultSet.getString("title"))
    );
  }
}
