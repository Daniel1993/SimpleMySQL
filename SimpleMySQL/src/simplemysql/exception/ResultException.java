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
public class ResultException extends Exception {

  public ResultException() {
  }

  public ResultException(String string) {
    super(string);
  }

  public ResultException(String string, Throwable thrwbl) {
    super(string, thrwbl);
  }

  public ResultException(Throwable thrwbl) {
    super(thrwbl);
  }
  
}
