/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemysql.query.clause;

/**
 * Use the ClauseCreator with the QueryBuilder statements.
 *
 * @author Daniel
 */
public abstract class ClauseCreator {

  /**
   * The StringBuilder used to create the clause.
   */
  protected final StringBuilder query = new StringBuilder();

  /**
   * Ends the current clause returning its representation.
   *
   * @return the ended clause
   */
  @Override
  public abstract String toString();
}
