/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemysql.query.clause;

/**
 * Create a clause to Insert statement.
 *
 * The created clause is in the form: '(' {value}* ')', where value is a string
 * representing a valid value in MySQL. Use this clause creator only with
 * QueryBuilder.INSERT statement.
 *
 * @author Daniel
 */
public class InsertClauseValues extends ClauseCreator {

  /**
   * Initiates a InsertClauseValues instance.
   *
   * Starts to append the '(' character in the clause. If you print the clause
   * before adding any columns the result will be the string "(".
   */
  public InsertClauseValues() {
    query.append("(");
  }

  /**
   * Appends a value in insert clause.
   *
   * Each value is surrounded with '\'' character and followed by ',', then when
   * toString method is called the clause is ended with ')'.
   *
   * @param values the values to be appended
   * @return this
   */
  public InsertClauseValues addValues(String... values) {
    for (String v : values) {
      String newValue = Utility.escape(v);
      query.append("'").append(newValue).append("', ");
    }
    return this;
  }

  /**
   * Ends the values in insert clause.
   *
   * You must not call addValues method after you call this method. Appends ')'
   * to the end of the clause or replases the end of the clause if ends with ','
   * ' ' to ')'.
   *
   * @return the columns in insert clause
   */
  @Override
  public String toString() {
    return Utility.endInsertClause(query);
  }

}
