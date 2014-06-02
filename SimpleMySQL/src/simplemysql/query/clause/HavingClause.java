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
public class HavingClause {

  private final StringBuilder query = new StringBuilder();

  public HavingClause() {
  }

  public HavingClause addClause(String function, boolean distinct,
          String functionTable, String functionColumn, String comparator,
          String value) {
    String newValue = Utility.escape(value);
    Utility.function(query, function, distinct, functionColumn);
    query.append(comparator).append("'").append(newValue).append("' ");
    return this;
  }

  public HavingClause addClause(String function, boolean distinct,
          String functionColumn, String comparator, String value) {
    String newValue = Utility.escape(value);
    Utility.function(query, function, distinct, functionColumn);
    query.append(comparator).append(" '").append(newValue).append("' ");
    return this;
  }

  public HavingClause AND() {
    query.append("AND ");
    return this;
  }

  public HavingClause OR() {
    query.append("OR ");
    return this;
  }

  @Override
  public String toString() {
    return query.toString();
  }

}
