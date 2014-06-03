package simplemysql.query.clause;

/**
 * Use the ClauseCreator with the QueryBuilder statements.
 *
 * @author Daniel Castro
 */
public abstract class Clause {

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

  public static CreateTableClause table() {
    return new CreateTableClause();
  }

  public static SelectClause select() {
    return new SelectClause();
  }

  public static FromClause from(String tableName) {
    return new FromClause(tableName);
  }

  public static FromClause from(String tableName, String asName) {
    return new FromClause(tableName, asName);
  }

  public static WhereClause where() {
    return new WhereClause();
  }

  public static HavingClause having() {
    return new HavingClause();
  }

  public static OrderByClause orderBy() {
    return new OrderByClause();
  }

  public static SetClause set() {
    return new SetClause();
  }

  public static InsertClauseColumns columns() {
    return new InsertClauseColumns();
  }

  public static InsertClauseValues values() {
    return new InsertClauseValues();
  }
}
