package simplemysql.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import simplemysql.SimpleAsyncMySQLTransactionHandler;
import simplemysql.SimpleMySQL;
import simplemysql.SimpleMySQLResult;

/**
 *
 * @author Daniel
 */
public class TestSimpleMySQL {
    
    public static final String server = "localhost:3306";
    public static final String database = "testdb";
    public static final String user = "root";
    public static final String pass = "";
    
    public TestSimpleMySQL() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("***********************");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect(server, user, pass, database);
        mysql.Query("CREATE TABLE IF NOT EXISTS `test`("
                + "`a` INTEGER,"
                + "`b` INTEGER,"
                + "`c` INTEGER,"
                + "PRIMARY KEY(`a`))");
        mysql.Query("INSERT INTO `test`(`a`, `b`, `c`) VALUES"
                + "('1', '2', '3'),"
                + "('4', '5', '6'),"
                + "('7', '8', '9')");
        mysql.close();
    }
    
    @After
    public void tearDown() {
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect(server, user, pass, database);
        mysql.Query("DROP TABLE IF EXISTS `test`");
        mysql.close();
    }
    
    @Test
    public void testSelect() {
        System.out.println("SimpleMySQL SELECT test");
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect(server, user, pass, database);
        SimpleMySQLResult result = mysql.Query("SELECT * FROM `test`");
        int comparator = 0;
        System.out.println("rows count: " + result.getNumRows());
        result.absolute(comparator);
        while(result.getNumRows() > result.getRow()) {
            Map<String, String> row = result.FetchAssoc();
            System.out.print("| a: " + row.get("a"));
            System.out.print(" | b: " + row.get("b"));
            System.out.print(" | c: " + row.get("c") + "|\n");
            comparator += Integer.parseInt(row.get("a"))
                    + Integer.parseInt(row.get("b"))
                    + Integer.parseInt(row.get("c"));
        }
        System.out.println("row: " + result.getRow());
        assertEquals(45, comparator);
        mysql.close();
        System.out.println("***********************");
    }
    
    @Test
    public void testInsert() {
        System.out.println("SimpleMySQL INSERT test");
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect(server, user, pass, database);
        mysql.Query("INSERT INTO `test`(`a`, `b`, `c`) VALUES(10, 10, 10)");
        System.out.print("Inserted\n| a: 10 | b: 10 | c: 10 |\n");
        SimpleMySQLResult result = mysql.Query("SELECT * FROM `test`"
                + "WHERE `a`='10'");
        int comparator = 0;
        Map<String, String> row = result.FetchAssoc();
        comparator += Integer.parseInt(row.get("b"))
                    + Integer.parseInt(row.get("c"));
        assertEquals(20, comparator);
        mysql.close();
        System.out.println("***********************");
    }
    
    @Test
    public void testUpdate() {
        System.out.println("SimpleMySQL UPDATE test");
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect(server, user, pass, database);
        mysql.Query("UPDATE `test` SET `b`=9213 WHERE `a`=1");
        
        SimpleMySQLResult result = mysql.Query("SELECT * FROM `test`"
                + "WHERE `a`='1'");
        Map<String, String> row = result.FetchAssoc();
        System.out.print("Updated\n| a: "
                + row.get("a") + " | b: "
                + row.get("b") + " | c: "
                + row.get("c") + " |\n");
        int comparator = Integer.parseInt(row.get("b"));
        assertEquals(9213, comparator);
        mysql.close();
        System.out.println("***********************");
    }
    
    @Test
    public void testTransationStateMachineCommit() {
        System.out.println("SimpleMySQL TransationStateMachineCommit test");
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect(server, user, pass, database);
        mysql.Query("UPDATE `test` SET `b`=9213 WHERE `a`=1");
        
        mysql.transactionBegin();
        mysql.Query("UPDATE `test` SET `b`=9214 WHERE `a`=1");
        mysql.Query("UPDATE `test` SET `b`=9215 WHERE `a`=1");
        mysql.Query("UPDATE `test` SET `b`=9216 WHERE `a`=1");
        mysql.transactionCommit();
        
        SimpleMySQLResult result = mysql.Query("SELECT * FROM `test`"
                + "WHERE `a`='1'");
        Map<String, String> row = result.FetchAssoc();
        System.out.print("Updated\n| a: "
                + row.get("a") + " | b: "
                + row.get("b") + " | c: "
                + row.get("c") + " |\n");
        int comparator = Integer.parseInt(row.get("b"));
        
        assertEquals(9216, comparator);
        mysql.close();
        System.out.println("***********************");
    }
    
    @Test
    public void testTransationCommit() {
        System.out.println("SimpleMySQL TransationCommit test");
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect(server, user, pass, database);
        mysql.Query("UPDATE `test` SET `b`=9213 WHERE `a`=1");
        
        mysql.Query(new SimpleAsyncMySQLTransactionHandler() {

            @Override
            public boolean processQuery(SimpleMySQLResult result, int atQuery) {
                return true;
            }
        }, "UPDATE `test` SET `b`=9214 WHERE `a`=1",
        "UPDATE `test` SET `b`=9215 WHERE `a`=1",
        "UPDATE `test` SET `b`=9216 WHERE `a`=1");
        
        SimpleMySQLResult result = mysql.Query("SELECT * FROM `test`"
                + "WHERE `a`='1'");
        Map<String, String> row = result.FetchAssoc();
        System.out.print("Updated\n| a: "
                + row.get("a") + " | b: "
                + row.get("b") + " | c: "
                + row.get("c") + " |\n");
        int comparator = Integer.parseInt(row.get("b"));
        
        assertEquals(9216, comparator);
        mysql.close();
        System.out.println("***********************");
    }
    
    @Test
    public void testTransationStateMachineRollback() {
        System.out.println("SimpleMySQL TransationStateMachineRollback test");
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect(server, user, pass, database);
        mysql.Query("UPDATE `test` SET `b`=9213 WHERE `a`=1");
        
        mysql.transactionBegin();
        mysql.Query("UPDATE `test` SET `b`=9214 WHERE `a`=1");
        mysql.Query("UPDATE `test` SET `b`=9215 WHERE `a`=1");
        mysql.Query("UPDATE `test` SET `b`=9216 WHERE `a`=1");
        mysql.transactionRollback();
        
        SimpleMySQLResult result = mysql.Query("SELECT * FROM `test`"
                + "WHERE `a`='1'");
        Map<String, String> row = result.FetchAssoc();
        System.out.print("Updated\n| a: "
                + row.get("a") + " | b: "
                + row.get("b") + " | c: "
                + row.get("c") + " |\n");
        int comparator = Integer.parseInt(row.get("b"));
        
        assertEquals(9213, comparator);
        mysql.close();
        System.out.println("***********************");
    }
    
    @Test
    public void testTransationRollback() {
        System.out.println("SimpleMySQL TransationCommit test");
        SimpleMySQL mysql = new SimpleMySQL();
        mysql.connect(server, user, pass, database);
        mysql.Query("UPDATE `test` SET `b`=9213 WHERE `a`=1");
        
        mysql.Query(new SimpleAsyncMySQLTransactionHandler() {

            @Override
            public boolean processQuery(SimpleMySQLResult result, int atQuery) {
                // rollbacks al last query
                return atQuery < 2;
            }
        }, "UPDATE `test` SET `b`=9214 WHERE `a`=1",
        "UPDATE `test` SET `b`=9215 WHERE `a`=1",
        "UPDATE `test` SET `b`=9216 WHERE `a`=1");
        
        SimpleMySQLResult result = mysql.Query("SELECT * FROM `test`"
                + "WHERE `a`='1'");
        Map<String, String> row = result.FetchAssoc();
        System.out.print("Updated\n| a: "
                + row.get("a") + " | b: "
                + row.get("b") + " | c: "
                + row.get("c") + " |\n");
        int comparator = Integer.parseInt(row.get("b"));
        
        assertEquals(9213, comparator);
        mysql.close();
        System.out.println("***********************");
    }
}
