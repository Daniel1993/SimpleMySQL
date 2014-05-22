/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemysql.query.clause;

/**
 * Create a clause to Insert statement.
 *
 * The created clause is in the form: '(' {Column}* ')', where column is a MySQL
 * table column. Use this clause creator only with QueryBuilder.INSERT
 * statement.
 *
 * @author Daniel
 */
public class InsertClauseColumns extends ClauseCreator {

    /**
     * Initiates a InsertClauseColumns instance.
     *
     * Starts to append the '(' character in the clause. If you print the clause
     * before adding any columns the result will be the string "(".
     */
    public InsertClauseColumns() {
        query.append("(");
    }

    /**
     * Appends a column in insert clause.
     *
     * Each appended column is followed by the ',' character, then when toString
     * method is called the clause is ended with ')'.
     *
     * @param columns the columns to be appended
     * @return this
     */
    public InsertClauseColumns addColumns(String... columns) {
        for (String c : columns) {
            query.append("`").append(c).append("`, ");
        }
        return this;
    }

    /**
     * Ends the columns in insert clause.
     *
     * You must not call addColumns method after you call this method. Appends
     * ')' to the end of the clause or replases the end of the clause if ends
     * with ',' ' ' to ')'.
     *
     * @return the columns in insert clause
     */
    @Override
    public String toString() {
        return Utility.endInsertClause(query);
    }

}
