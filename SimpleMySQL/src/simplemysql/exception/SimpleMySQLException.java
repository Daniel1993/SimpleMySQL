/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemysql.exception;

/**
 *
 * @author Daniel
 */
public class SimpleMySQLException extends Exception {

  public SimpleMySQLException() {
  }

  public SimpleMySQLException(String string) {
    super(string);
  }

  public SimpleMySQLException(String string, Throwable thrwbl) {
    super(string, thrwbl);
  }

  public SimpleMySQLException(Throwable thrwbl) {
    super(thrwbl);
  }

}
