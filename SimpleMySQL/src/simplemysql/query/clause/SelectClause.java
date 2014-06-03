package simplemysql.query.clause;

/**
 *
 * @author Daniel Castro
 */
public class SelectClause {

  private final StringBuilder query = new StringBuilder();

  public SelectClause() {
  }

  public SelectClause addColumn(String column) {
    if (column.equals("*")) {
      query.append("* ");
    } else {
      query.append("`").append(column).append("`, ");
    }
    return this;
  }

  public SelectClause addColumn(String table, String column) {
    query.append("`").append(table).append("`.`").append(column).
            append("`, ");
    return this;
  }

  public SelectClause addColumnAS(String column, String asName) {
    query.append("`").append(column).append("` AS ").append("`").
            append(asName).append("`, ");
    return this;
  }

  public SelectClause addColumnAS(String table, String column, String asName) {
    query.append("`").append(table).append("`.`").append(column).
            append("` AS ").append("`").append(asName).append("`, ");
    return this;
  }

  public SelectClause addFunctionAS(String function, boolean distinct,
                                    String column, String asName) {
    Utility.function(query, function, distinct, column);
    query.append(" AS `").append(asName).append("`, ");
    return this;
  }

  public SelectClause addFunctionAS(String function, boolean distinct,
                                    String table, String column, String asName) {
    Utility.function(query, function, distinct, table, column);
    query.append(" AS `").append(asName).append("`, ");
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
