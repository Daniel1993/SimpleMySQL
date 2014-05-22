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
public class SetClause {
    private final StringBuilder query = new StringBuilder();

    public SetClause() {
    }

    public SetClause add(String column, String value) {
        String newValue = Utility.escape(value);
        query.append("`").append(column).append("`='").append(newValue).
                append("', ");
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
