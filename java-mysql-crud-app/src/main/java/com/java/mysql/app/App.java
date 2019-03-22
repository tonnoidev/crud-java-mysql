package com.java.mysql.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        System.out.println("Java connection to MySQL with CRUD Project");
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/testdb", "root", "p@ssw0rd2535");
            conn.setAutoCommit(false);
            System.out.println("Open Connection");

            // show all data
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("select * from cars")) {
                    while (rs.next()) {
                        System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
                    }
                }
            }

            // show all data with header
            try (Statement stmt = conn.createStatement()) {
                try (ResultSet rs = stmt.executeQuery("select * from cars")) {
                    int count = 0;
                    int cols = 0;
                    while (rs.next()) {
                        if (count == 0) {
                            cols = rs.getMetaData().getColumnCount();
                            for (int i = 1; i <= cols; i++) {
                                System.out.print(rs.getMetaData().getColumnName(i) + " ");
                            }
                            System.out.println("");
                            count++;
                        }
                        for (int i = 1; i <= cols; i++) {
                            System.out.print(rs.getString(i) + " ");
                        }
                        System.out.println("");
                    }
                }
            }
            
            // insert
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("insert into cars values(8, 'Toyota', 38000)");
            }
            
            // update
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("update cars set price=90000 where id=8");
            }
            
            // delete
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("delete from cars where id=8");
            }
            
            conn.commit();
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Close Connection");
                } catch (SQLException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
