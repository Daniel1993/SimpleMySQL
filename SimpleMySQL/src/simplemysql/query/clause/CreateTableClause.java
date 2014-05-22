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
public class CreateTableClause {
    private final StringBuilder query = new StringBuilder();

    public CreateTableClause() {
        query.append("(");
    }

    /**
     *
     * @param column the column name
     * @param typeAndConstraints column type like INT, VARCHAR(56)... and/or
     * constraints like NOT NULL, DEFAULT 8...
     * @return this
     */
    public CreateTableClause addColumn(String column, String typeAndConstraints) {
        query.append("`").append(column).append("` ").append(typeAndConstraints).
                append(", ");
        return this;
    }

    public CreateTableClause addPrimaryKey(String column) {
        query.append("PRIMARY KEY(`").append(column).append("`), ");
        return this;
    }

    public CreateTableClause addForeignKey(String column, String foreighTable,
            String foreignColumn, String options) {
        query.append("FOREIGN KEY(`").append(column).append("`) ").
                append("REFERENCES `").append(foreighTable).append("`(`").
                append(foreignColumn).append("`) ").append(options).append(", ");
        return this;
    }

    public CreateTableClause addForeignKey(String column, String foreighTable,
            String foreignColumn) {
        return addForeignKey(column, foreighTable, foreignColumn, "");
    }

    @Override
    public String toString() {
        if (query.length() > 0) {
            query.replace(query.length() - 2, query.length(), ") ");
        }
        return query.toString();
    }
    
}
