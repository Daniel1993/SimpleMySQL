package simplemysql.query.clause;

/**
 *
 * @author Daniel Castro
 */
public class HavingClause {

  private final StringBuilder query = new StringBuilder();

  public HavingClause() {
  }

  public HavingClause addClause(String function, boolean distinct,
                                String functionTable, String functionColumn,
                                String comparator,
                                String value) {
    String newValue = Utility.escape(value);
    Utility.function(query, function, distinct, functionColumn);
    query.append(comparator).append("'").append(newValue).append("' ");
    return this;
  }

  public HavingClause addClause(String function, boolean distinct,
                                String functionColumn, String comparator,
                                String value) {
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
