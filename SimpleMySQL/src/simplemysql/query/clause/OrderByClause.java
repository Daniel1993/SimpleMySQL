package simplemysql.query.clause;

/**
 *
 * @author Daniel Castro
 */
public class OrderByClause {

  private final StringBuilder query = new StringBuilder();

  public OrderByClause() {
  }

  public OrderByClause addColumn(String column) {
    query.append("`").append(column).append("`, ");
    return this;
  }

  /**
   *
   * @param column
   * @param direction can be ASC ascendent or DESC descendent
   * @return
   */
  public OrderByClause addColumn(String column, String direction) {
    addColumn(column);
    query.append(direction).append(", ");
    return this;
  }

  public OrderByClause addFunction(String function) {
    query.append(function).append(", ");
    return this;
  }

  public OrderByClause addFunction(String function, String direction) {
    addFunction(function);
    query.append(direction).append(", ");
    return this;
  }

  @Override
  public String toString() {
    if (query.length() > 0 && query.toString().endsWith(", ")) {
      query.replace(query.length() - 2, query.length(), " ");
    }
    return query.toString();
  }

}
