
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rossy
 */
public class Connect {
    public Connection conn;
    public Statement stmt;
    public ResultSet rs;
    
    public Connect(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:librarydb.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
    public void addBook(String title, String authorFirstName, String authorLastName, String genre, int yearPublished) {
    try {
        stmt = conn.createStatement();
        String query = "INSERT INTO books (Title, AuthorFirstName, AuthorLastName, PublishedYear, Genre) VALUES ('" + title + "', '" + authorFirstName + "', '" + authorLastName + "', " + yearPublished + ", '" + genre + "')"; 

        stmt.executeUpdate(query);
        System.out.println("Book added successfully!");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
    public void addBorrowedBook(String bookId, String borrowerId){
        try {
        stmt = conn.createStatement();
        String query = "INSERT INTO BooksBorrowed (BookId, BorrowerId, BorrowedDate, DateToReturn) VALUES ('" + bookId+ "', '" + borrowerId +  ")";
        stmt.executeUpdate(query);
        System.out.println(query);
        System.out.println("Book added successfully!");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    }
    public ArrayList<String> select(String[] columnsArray, String table){
        ArrayList<String> data = new ArrayList<String>();
        
        String columnsString = String.join(", ", columnsArray);
        String sql = "SELECT " + columnsString + " FROM " + table;
        
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row += rs.getString(columnsArray[i]) + "---";
                }
                data.add(row);
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    public ArrayList<String> selectSearch(String[] columnsArray, String table, String whereValue, String selectedOption){
        ArrayList<String> data = new ArrayList<String>();
        String columnString = String.join(",", columnsArray);
        String sql = "SELECT " + columnString + " FROM " + table + " WHERE " + whereValue + " == " + selectedOption;
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row += rs.getString(columnsArray[i]) + "---";
                }
                data.add(row);
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
     public ArrayList<String> selectGroup(String[] columnsArray, String group, String table){
        ArrayList<String> data = new ArrayList<String>();
        
        String columnsString = String.join(", ", columnsArray);
        String sql = "SELECT " + columnsString + " FROM " + table + " GROUP BY " + group;
        
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row += rs.getString(columnsArray[i]) + "---";
                }
                data.add(row);
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
     public ArrayList<String> selectWhere(String[] columnsArray, int[] whereColIndex, String[] whereValue, String table){
        ArrayList<String> data = new ArrayList<String>();
        
        String columnsString = String.join(", ", columnsArray);
        
        String sql = "SELECT " + columnsString + " FROM " + table + " WHERE ";
            for (int i = 0; i < whereColIndex.length; i++) {
                sql += columnsArray[whereColIndex[i]] + " LIKE '%" + whereValue[i] + "%' OR ";
            }
            System.out.println(sql);
        sql = sql.substring(0, sql.length() - 4);
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row += rs.getString(columnsArray[i]) + "---";
                }
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
     
    public void delete(String column, int id, String table){
        String sql = "DELETE FROM " + table + " WHERE " + column + " = " + id;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    
    
    public void insertBookBorrowed(String[] columnsArray, String[] valuesArray, String table){
        String columnsString = String.join(", ", columnsArray);
        String valuesString = "'" + String.join("', '", valuesArray) + "'";
        
        valuesString = valuesString.replace("''", "null");
        
        String sql = "INSERT INTO " + table + " (" + columnsString + ") VALUES (" 
                + valuesString + ")";
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insert(String[] columnsArray, String[] valuesArray, String table){
        String columnsString = String.join(", ", columnsArray);
        String valuesString = "'" + String.join("', '", valuesArray) + "'";
        
        valuesString = valuesString.replace("''", "null");
        
        String sql = "INSERT INTO " + table + " (" + columnsString + ") VALUES (" 
                + valuesString + ")";
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void update(String[] columnsArray, String[] valuesArray, String whereColumn, int id, String table){
        String sql =  "UPDATE " + table + " SET ";
        for (int i = 0; i < columnsArray.length; i++) {
            sql = sql + columnsArray[i] + " = " + 
                    (valuesArray[i].equals("") ? "null, " : "'" + valuesArray[i] + "', ");
        }
        sql = sql.substring(0, sql.length() - 2);
 
        sql = sql + " WHERE " + whereColumn + " = " + id;
        
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void close(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Throwable ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
