/*
 * 
 * $Id$
 * 
 * Software License Agreement (BSD License)
 * 
 * Copyright (c) 2011, The Daniel Morante Company, Inc.
 * All rights reserved.
 * 
 * Redistribution and use of this software in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *   Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 * 
 *   Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 * 
 *   Neither the name of The Daniel Morante Company, Inc. nor the names of its
 *   contributors may be used to endorse or promote products
 *   derived from this software without specific prior
 *   written permission of The Daniel Morante Company, Inc.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package simplemysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import simplemysql.exception.ResultException;

/**
 *
 * @author Daniel Morante <daniel@morante.net>
 */
public class SimpleMySQLResult {

  private ResultSet RESULT_SET = null;
  private int POSITION = 0;

  /**
   * SimpleMySQL Result Object for simplified database access and manipulation.
   *
   * @param result A standard Java SQL ResultSet
   */
  public SimpleMySQLResult(ResultSet result) {
    RESULT_SET = result;
  }

  /**
   * Get the current row as a standard String array.
   *
   * @return String Array with all columns
   * @throws simplemysql.exception.ResultException
   */
  public String[] FetchArray() throws ResultException {
    String[] columns = null;
    try {
      RESULT_SET.next();
      columns = new String[RESULT_SET.getMetaData().getColumnCount()];
      for (int column = 1; column <= RESULT_SET.getMetaData().getColumnCount(); column++) {
        columns[column - 1] = RESULT_SET.getString(column);
      }
    } catch (SQLException e) {
      throw new ResultException("Can't fetch array", e);
    }
    return columns;
  }

  /**
   * Get the current row as a hash map.
   *
   * Similar to an associative array. Each column can be referenced by name
   * using the get() method.
   *
   * @return Map object
   * @throws simplemysql.exception.ResultException if SQLException ocurred
   */
  public Map<String, String> FetchAssoc() throws ResultException {
    Map<String, String> map = null;
    try {
      RESULT_SET.next();
      map = new HashMap<>();
      for (int column = 1; column <= RESULT_SET.getMetaData().getColumnCount(); column++) {
        map.put(RESULT_SET.getMetaData().getColumnLabel(column), RESULT_SET.getString(column));
      }
    } catch (SQLException e) {
      throw new ResultException("Can't fetch assoc", e);
    }
    return map;
  }

  private void save_position() throws ResultException {
    try {
      if (RESULT_SET.isBeforeFirst()) {
        POSITION = 0;
      } else if (RESULT_SET.isAfterLast()) {
        POSITION = -1;
      } else {
        POSITION = getRow();
      }
    } catch (SQLException e) {
      throw new ResultException("Can't save position", e);
    }
  }

  private void restore_position() throws ResultException {
    try {
      if (POSITION == 0) {
        RESULT_SET.beforeFirst();
      } else if (POSITION == -1) {
        RESULT_SET.afterLast();
      } else {
        absolute(POSITION);
      }
    } catch (SQLException e) {
      throw new ResultException("Can't restore position", e);
    }
  }

  /**
   * Total number of rows in this result.
   *
   * @return number of rows in result set, or 0 if empty
   */
  public int getNumRows() {
    int returnValue = 0;
    try {
      save_position();
      RESULT_SET.last();
      returnValue = RESULT_SET.getRow();
      restore_position();
    } finally {
      return returnValue;
    }

  }

  /**
   * Gets the current row number.
   *
   * @return current row number
   * @see ResultSet#getRow()
   */
  public int getRow() {
    try {
      return RESULT_SET.getRow();
    } catch (SQLException e) {
      return 0;
    }
  }

  /**
   * Lets you reference the wrapped ResultSet object directly for advanced
   * functions.
   *
   * @return {@link ResultSet} object
   */
  public ResultSet getResultSet() {
    return RESULT_SET;
  }

  /**
   * Sets to cursor to a specific row.
   *
   * @param row the row number to go to
   * @return True on success
   * @see ResultSet#absolute(int)
   */
  public boolean absolute(int row) {
    try {
      return RESULT_SET.absolute(row);
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Moves to the next row.
   *
   * @return True on success; false if there are no more rows.
   * @see ResultSet#next()
   */
  public boolean next() {
    try {
      return RESULT_SET.next();
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Moves the cursor to the previous row.
   *
   * @return true if the cursor is now positioned on a valid row; false if the
   * cursor is positioned before the first row
   * @see ResultSet#previous()
   */
  public boolean previous() {
    try {
      return RESULT_SET.previous();
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Moves the cursor to the first row in this ResultSet object.
   *
   * @return true if the cursor is on a valid row; false if there are no rows
   * @see ResultSet#first()
   */
  public boolean first() {
    try {
      return RESULT_SET.first();
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Moves the cursor to the last row.
   *
   * @return true if the cursor is on a valid row; false if there are no rows
   * @see ResultSet#last()
   */
  public boolean last() {
    try {
      return RESULT_SET.last();
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Moves the cursor to the front, just before the first row. This method has
   * no effect if the result set contains no rows.
   *
   * @throws simplemysql.exception.ResultException if SQLException ocurred
   * @see ResultSet#beforeFirst() 
   */
  public void beforeFirst() throws ResultException {
    try {
      RESULT_SET.beforeFirst();
    } catch (SQLException e) {
      throw new ResultException("Can't move before first", e);
    }
  }

  /**
   * Moves the cursor to the end, just after the last row. This method has no
   * effect if the result set contains no rows.
   *
   * @throws simplemysql.exception.ResultException if SQLException ocurred
   * @see ResultSet#afterLast()
   */
  public void afterLast() throws ResultException {
    try {
      RESULT_SET.afterLast();
    } catch (SQLException e) {
      throw new ResultException("Can't move after last", e);
    }
  }

  /**
   * Closes this resource, relinquishing any underlying resources.
   *
   * @throws simplemysql.exception.ResultException if SQLException ocurred
   * @see ResultSet#close()
   */
  public void close() throws ResultException {
    try {
      RESULT_SET.close();
    } catch (SQLException e) {
      throw new ResultException("Can't close resourse", e);
    }
  }

  /**
   * Retrieves the value of the designated column in the current row.
   *
   * @param columnLabel the label for the column specified with the SQL AS
   * clause. If the SQL AS clause was not specified, then the label is the name
   * of the column
   * @return the column value; if the value is SQL NULL, the value returned is
   * null
   * @throws simplemysql.exception.ResultException if SQLException ocurred
   * @see ResultSet#getString(java.lang.String) 
   */
  public String getString(String columnLabel) throws ResultException {
    String returnValue = null;
    save_position();
    try {
      if (RESULT_SET.isBeforeFirst()) {
        RESULT_SET.next();
      } else if (RESULT_SET.isAfterLast()) {
        RESULT_SET.previous();
      }
      returnValue = RESULT_SET.getString(columnLabel);
    } catch (SQLException e) {
      throw new ResultException("Can't get String", e);
    } finally {
      restore_position();
    }
    return returnValue;
  }

  /**
   * Retrieves the value of the designated column in the current row.
   * 
   * @param columnIndex the first column is 1, the second is 2, ...
   * @return the column value; if the value is SQL NULL, the value returned is
   * null
   * @throws simplemysql.exception.ResultException if SQLException ocurred
   * @see ResultSet#getString(int) 
   */
  public String getString(int columnIndex) throws ResultException {
    String returnValue = null;
    save_position();
    try {
      if (RESULT_SET.isBeforeFirst()) {
        RESULT_SET.next();
      } else if (RESULT_SET.isAfterLast()) {
        RESULT_SET.previous();
      }
      returnValue = RESULT_SET.getString(columnIndex);
    } catch (SQLException e) {
      throw new ResultException("Can't get String", e);
    } finally {
      restore_position();
    }
    return returnValue;
  }

}
