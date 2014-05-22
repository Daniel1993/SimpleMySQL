package simplemysql.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simplemysql.query.clause.CreateTableClause;
import simplemysql.query.clause.FromClause;
import static org.junit.Assert.*;
import simplemysql.query.QueryBuilder;
import simplemysql.query.clause.SelectClause;
import simplemysql.query.clause.SetClause;
import simplemysql.query.clause.WhereClause;

/**
 *
 * @author Daniel
 */
public class TestQueryPreparer {
    
    public TestQueryPreparer() {
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
    }
    
    @After
    public void tearDown() {
        System.out.println("***********************");
    }

     @Test
     public void testSelect1() {
         System.out.println("test SELECT * FROM `testdb` ");
         QueryBuilder query = new QueryBuilder();
         query.SELECT(false).FROM("testdb");
         assertEquals("SELECT * FROM `testdb` ", query.toString());
     }
     
     @Test
     public void testSelect2() {
         System.out.println("test SELECT * FROM `a` NATURAL JOIN `b` ");
         QueryBuilder query = new QueryBuilder();
         FromClause from = new FromClause("a");
         from.NATURAL_JOIN("b");
         query.SELECT(false).FROM(from);
         assertEquals("SELECT * FROM `a` NATURAL JOIN `b` ", query.toString());
     }
     
     @Test
     public void testSelect3() {
         System.out.println("test SELECT * FROM `a` NATURAL JOIN `b` WHERE `a`.`a`=5 ");
         QueryBuilder query = new QueryBuilder();
         WhereClause cond = new WhereClause();
         cond.compareWithValue("a", "a", "=", "5");
         FromClause from = new FromClause("a");
         from.NATURAL_JOIN("b");
         query.SELECT(false).FROM(from).WHERE(cond);
         assertEquals("SELECT * FROM `a` NATURAL JOIN `b` WHERE `a`.`a`='5' ",
                 query.toString());
     }
     
     @Test
     public void testSelect4() {
         System.out.println("test SELECT a, b FROM `a`, `b`, `c` WHERE `a`.`a`=`a`.`b` ");
         QueryBuilder query = new QueryBuilder();
         WhereClause cond = new WhereClause();
         cond.compareWithColumn("a", "a", "=", "a", "b");
         SelectClause select = new SelectClause();
         select.addColumn("a").addColumn("b");
         query.SELECT(false).FROM("a", "b", "c").WHERE(cond);
         assertEquals("SELECT * FROM `a`, `b`, `c` WHERE `a`.`a`=`a`.`b` ",
                 query.toString());
     }
     
     @Test
     public void testUpdate1() {
         System.out.println("test UPDATE `a` SET `a`='2', `c`='4' WHERE `b`='3' ");
         QueryBuilder query = new QueryBuilder();
         SetClause attr = new SetClause();
         WhereClause cond = new WhereClause();
         attr.add("a", "2").add("c", "4");
         cond.compareWithValue("b", "=", "3");
         query.UPDATE("a").SET(attr).WHERE(cond);
         assertEquals("UPDATE `a` SET `a`='2', `c`='4' WHERE `b`='3' ",
                 query.toString());
     }
     
     @Test
     public void testAlterTable() {
         System.out.println("test UPDATE `a` SET `a`='2', `c`='4' WHERE `b`='3' ");
         QueryBuilder query = new QueryBuilder();
         SetClause attr = new SetClause();
         WhereClause cond = new WhereClause();
         attr.add("a", "2").add("c", "4");
         cond.compareWithValue("b", "=", "3");
         query.UPDATE("a").SET(attr).WHERE(cond);
         assertEquals("UPDATE `a` SET `a`='2', `c`='4' WHERE `b`='3' ",
                 query.toString());
     }
     
     @Test
     public void testCreateTable1() {
         System.out.println("test CREATE TABLE `a`(`a` INT NOT NULL, `b` INT"
                 + " UNIQUE, PRIMARY KEY(`a`)) ");
         QueryBuilder query = new QueryBuilder();
         CreateTableClause struct = new CreateTableClause();
         struct.addColumn("a", "INT NOT NULL").addColumn("b", "INT UNIQUE").
                 addPrimaryKey("a");
         query.CREATE_TABLE(false, "a", struct);
         assertEquals("CREATE TABLE `a`(`a` INT NOT NULL, `b` INT UNIQUE,"
                 + " PRIMARY KEY(`a`)) ",
                 query.toString());
     }
     
      @Test
     public void testCreateTable2() {
         System.out.println("test CREATE TABLE `a`(`a` INT, `b` INT, PRIMARY"
                 + " KEY(`a`), FOREIGN KEY(`b`) REFERENCES `b`(`b`)) ");
         QueryBuilder query = new QueryBuilder();
         CreateTableClause struct = new CreateTableClause();
         struct.addColumn("a", "INT").addColumn("b", "INT").addPrimaryKey("a").
                 addForeignKey("b", "b", "b");
         query.CREATE_TABLE(false, "a", struct);
         assertEquals("CREATE TABLE `a`(`a` INT, `b` INT, PRIMARY KEY(`a`),"
                 + " FOREIGN KEY(`b`) REFERENCES `b`(`b`) ) ",
                 query.toString());
     }
}
