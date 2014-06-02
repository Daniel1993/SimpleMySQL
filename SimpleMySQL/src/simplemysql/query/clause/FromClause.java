/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemysql.query.clause;

/**
 *
 * @author Daniel
 */
public class FromClause {

  private final StringBuilder query = new StringBuilder();

  public FromClause(String table) {
    query.append("`").append(table).append("` ");
  }

  public FromClause(String table, String asName) {
    query.append("`").append(table).append("` AS `").append(asName).
            append("` ");
  }

  /**
   *
   * @param table
   * @return this
   */
  public FromClause CARTESIAN_PRODUCT(String table) {
    query.append(", `").append(table).append("` ");
    return this;
  }

  public FromClause CARTESIAN_PRODUCT_AS(String table, String asName) {
    query.append(", `").append(table).append("` AS `").append(asName).
            append("` ");
    return this;
  }

  public FromClause NATURAL_JOIN(String table) {
    query.append("NATURAL JOIN `").append(table).append("` ");
    return this;
  }

  public FromClause NATURAL_JOIN_AS(String table, String asName) {
    query.append("NATURAL JOIN `").append(table).append("` AS `").
            append(asName).append("` ");
    return this;
  }

  @Override
  public String toString() {
    return query.toString();
  }

}
