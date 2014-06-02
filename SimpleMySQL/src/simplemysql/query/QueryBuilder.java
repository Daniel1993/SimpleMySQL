/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemysql.query;

import simplemysql.query.clause.InsertClauseValues;
import simplemysql.query.clause.FromClause;
import simplemysql.query.clause.WhereClause;
import simplemysql.query.clause.InsertClauseColumns;
import simplemysql.query.clause.CreateTableClause;
import simplemysql.query.clause.SetClause;
import simplemysql.query.clause.SelectClause;
import simplemysql.query.clause.OrderByClause;
import simplemysql.query.clause.HavingClause;

/**
 * Creates a MySQL query.
 *
 * This class is intended to help the programer printing the MySQL queries
 * strings. It is not intended to produce correct queries, thats up to the
 * programer.
 *
 * @author Daniel
 */
public class QueryBuilder {

  /**
   * The StringBuilder query.
   *
   * It's used to create the query appending new elements. At end, is used the
   * toString() method to produce the MySQL query string.
   */
  protected final StringBuilder query = new StringBuilder();

  /**
   * Initializates a QueryPreparer.
   */
  public QueryBuilder() {
  }

  /**
   * Appends the select clause.
   *
   * @param columns the columns on the select clause
   */
  private void selectColumns(String[] columns) {
    boolean all = false;
    for (String s : columns) {
      if (s.equals("*")) {
        query.append("* ");
        all = true;
      } else {
        query.append("`").append(s).append("`, ");
      }
    }
    if (columns.length > 0 && !all) {
      query.replace(query.length() - 2, query.length(), " ");
    }
  }

  /**
   * Appends a "SELECT [DISTINCT] {columns}" statement.
   *
   * @param distinct if needed to print DISTINCT
   * @param columns the columns on select clause
   * @return this
   */
  public QueryBuilder SELECT(boolean distinct, String... columns) {
    query.append("SELECT ");
    if (distinct) {
      query.append("DISTINCT ");
    }
    selectColumns(columns);
    return this;
  }

  /**
   * Appends a "SELECT [DISTINCT] * " statement.
   *
   * @param distinct if needed to print DISTINCT
   * @return this
   */
  public QueryBuilder SELECT(boolean distinct) {
    query.append("SELECT ");
    if (distinct) {
      query.append("DISTINCT ");
    }
    query.append("* ");
    return this;
  }

  /**
   *
   * @param distinct if needed to print DISTINCT
   * @param clause a SelectClause for complex statements
   * @return this
   * @see SelectClause
   */
  public QueryBuilder SELECT(boolean distinct, SelectClause clause) {
    query.append("SELECT ");
    if (distinct) {
      query.append("DISTINCT ");
    }
    query.append(clause.toString());
    return this;
  }

  public QueryBuilder DELETE(boolean ignore) {
    query.append("DELETE ");
    if (ignore) {
      query.append("IGNORE ");
    }
    return this;
  }

  /**
   *
   * @param tables
   * @return this
   */
  public QueryBuilder FROM(String... tables) {
    query.append("FROM ");
    for (String s : tables) {
      query.append("`").append(s).append("`, ");
    }
    if (tables.length > 0) {
      query.replace(query.length() - 2, query.length(), " ");
    }
    return this;
  }

  /**
   *
   * @param clause
   * @return this
   */
  public QueryBuilder FROM(FromClause clause) {
    query.append("FROM ").append(clause.toString());
    return this;
  }

  /**
   *
   * @param condition
   * @return this
   */
  public QueryBuilder WHERE(WhereClause condition) {
    query.append("WHERE ").append(condition.toString());
    return this;
  }

  public QueryBuilder UPDATE(String table) {
    query.append("UPDATE `").append(table).append("` ");
    return this;
  }

  public QueryBuilder SET(SetClause columns) {
    query.append("SET ").append(columns.toString());
    return this;
  }

  /**
   *
   * @param table
   * @param columns
   * @param values
   * @return this
   */
  public QueryBuilder INSERT(String table, InsertClauseColumns columns,
          InsertClauseValues... values) {
    query.append("INSERT INTO `").append(table).append("`");
    query.append(columns.toString());
    query.append("VALUES ");
    for (InsertClauseValues v : values) {
      query.append(v.toString()).append(", ");
    }
    if (values.length > 0) {
      query.replace(query.length() - 2, query.length(), " ");
    }
    return this;
  }

  public QueryBuilder CREATE_TABLE(boolean ifNotExists, String tableName,
          CreateTableClause struct) {
    query.append("CREATE TABLE ");
    if (ifNotExists) {
      query.append("IF NOT EXISTS ");
    }
    query.append("`").append(tableName).append("`").
            append(struct.toString());
    return this;
  }

  public QueryBuilder ORDER_BY(OrderByClause orderBy) {
    query.append("ORDER BY ").append(orderBy.toString());
    return this;
  }

  public QueryBuilder GROUP_BY(String... columns) {
    query.append("GROUP BY ");
    for (String c : columns) {
      query.append("`").append(c).append("`, ");
    }
    if (columns.length > 0) {
      query.replace(query.length() - 2, query.length(), " ");
    }
    return this;
  }

  public QueryBuilder HAVING(HavingClause clause) {
    query.append("HAVING ");
    query.append(clause);
    return this;
  }

  public QueryBuilder ALTER_TABLE_ADD(boolean ignore, String table,
          String column, String columnDefinition) {
    query.append("ALTER ");
    if (ignore) {
      query.append("IGNORE ");
    }
    query.append("TABLE `").append(table).append("` ADD `").append(column).
            append("` ").append(columnDefinition).append(" ");
    return this;
  }

  public QueryBuilder ALTER_TABLE_MODIFY(boolean ignore, String table,
          String column, String columnDefinition) {
    query.append("ALTER ");
    if (ignore) {
      query.append("IGNORE ");
    }
    query.append("TABLE `").append(table).append("` MODIFY `").
            append(column).append("` ").append(columnDefinition).
            append(" ");
    return this;
  }

  public QueryBuilder ALTER_TABLE_DROP(boolean ignore, String table,
          String column) {
    query.append("ALTER ");
    if (ignore) {
      query.append("IGNORE ");
    }
    query.append("TABLE `").append(table).append("` DROP `").
            append(column).append("` ");
    return this;
  }

  public QueryBuilder DROP_TABLE(boolean ifExists, String table) {
    query.append("DROP TABLE ");
    if (ifExists) {
      query.append("IF EXISTS ");
    }
    query.append("`").append(table).append("` ");
    return this;
  }

  public QueryBuilder LIMIT(String firstIndex, String duration) {
    query.append("LIMIT ").append(firstIndex).append(", ").append(duration);
    return this;
  }

  @Override
  public String toString() {
    return query.toString();
  }
}
