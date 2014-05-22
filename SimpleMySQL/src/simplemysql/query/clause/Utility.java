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
class Utility {

    /**
     * Adds a MySQL function to current query.
     *
     * @param query the StringBuilder query
     * @param function a MySQL function like MAX, MIN, AVG...
     * @param distinct if is distinct FUNCTION([DISTINCT] column)
     * @param column the column to apply function
     */
    static void function(StringBuilder query, String function, boolean distinct,
            String column) {
        query.append(function).append("(");
        if (distinct) {
            query.append("DISTINCT ");
        }
        query.append("`").append(column).append("`) ");
    }

    /**
     * Adds a MySQL function to current query.
     *
     * @param query the StringBuilder query
     * @param function a MySQL function like MAX, MIN, AVG...
     * @param distinct if is distinct FUNCTION([DISTINCT] column)
     * @param table the table that contains the column
     * @param column the column to apply function
     */
    static void function(StringBuilder query, String function, boolean distinct,
            String table, String column) {
        query.append(function).append("(");
        if (distinct) {
            query.append("DISTINCT ");
        }
        query.append("`").append(table).append("`.`").append(column).
                append("`) ");
    }

    static String escape(String str) {
        return str.replaceAll("['\"\\\\]", "\\\\$0");
    }
    
    static String endInsertClause(StringBuilder query) {
        if (query.toString().endsWith(", ")) {
            query.replace(query.length() - 2, query.length(), ") ");
        }
        return query.toString();
    }
    
}
