/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemysql.query.clause;

import simplemysql.query.QueryBuilder;

/**
 *
 * @author Daniel
 */
public class WhereClause {

  private final StringBuilder query = new StringBuilder();

  public WhereClause() {
  }

  public WhereClause AND() {
    query.append("AND ");
    return this;
  }

  public WhereClause OR() {
    query.append("OR ");
    return this;
  }

  public WhereClause IN(QueryBuilder in) {
    query.append("IN( ").append(in.toString()).append(") ");
    return this;
  }

  public WhereClause NOT_IN(QueryBuilder in) {
    query.append("NOT IN( ").append(in.toString()).append(") ");
    return this;
  }

  public WhereClause EXISTS(QueryBuilder in) {
    query.append("NOT EXISTS( ").append(in.toString()).append(") ");
    return this;
  }

  public WhereClause NOT_EXISTS(QueryBuilder in) {
    query.append("NOT EXISTS( ").append(in.toString()).append(") ");
    return this;
  }

  public WhereClause compareWithValue(String column, String comparator,
          String value) {
    String newValue = Utility.escape(value);
    query.append("`").append(column).append("`").append(comparator).
            append("'").append(newValue).append("' ");
    return this;
  }

  public WhereClause compareWithValue(String table, String column,
          String comparator, String value) {
    String newValue = Utility.escape(value);
    query.append("`").append(table).append("`.`").append(column).append("`").
            append(comparator).append("'").append(newValue).append("' ");
    return this;
  }

  public WhereClause compareWithColumn(String column1, String comparator,
          String column2) {
    query.append("`").append(column1).append("`").append(comparator).
            append("`").append(column2).append("` ");
    return this;
  }

  public WhereClause compareWithColumn(String table1, String column1,
          String comparator, String table2, String column2) {
    query.append("`").append(table1).append("`.`").append(column1).
            append("`").append(comparator).append("`").append(table2).
            append("`.`").append(column2).append("` ");
    return this;
  }

  @Override
  public String toString() {
    return query.toString();
  }

}
