/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbproject;

/**
 *
 * @author Sergio
 */
import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class DBConnection {
    //database for music player
    
    Connection conn;
    Statement stmt;
    ResultSet rs;
    int y;
    PreparedStatement ps;
    
    public DBConnection ()throws ClassNotFoundException, SQLException {
       try{
           Class.forName("org.apache.derby.jdbc.ClientDriver");
       }
       catch(ClassNotFoundException e){
           System.out.println("Class not found "+ e);
       }
       try{
           conn = DriverManager.getConnection("jdbc:derby://localhost:1527/DBRecordingGroup", "sergio", "sergio");
           stmt = conn.createStatement();

       }
       catch(SQLException e){
           System.out.println("SQL Exception " + e);
       }
    }
    //GET
    public String getGroupName(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("gName");
        }
        return x;
    }
    public String getGroupSinger(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("gSinger");
        }
        return x;
    }
    public int getGroupYear(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        int x=0;
        if(rs.next()){
            x = rs.getInt("gYearFormed");
        }
        return x;
    }
    public String getGroupGenre(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("gGenre");
        }
        return x;
    }
    
    public String getAlbumTitle(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("aTitle");
        }
        return x;
    }
    public String getAlbumDate(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("aDateRec");
        }
        return x;
    }
    public String getAlbumLength(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("aLength");
        }
        return x;
    }
    public int getAlbumSongs(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        int x=0;
        if(rs.next()){
            x = rs.getInt("aNomOfSongs");
        }
        return x;
    }
    public String getStudioName(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("sName");
        }
        return x;
    }
    public String getStudioAddr(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("sAddr");
        }
        return x;
    }
    public String getStudioOwner(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("sOwner");
        }
        return x;
    }
    public String getStudioPhone(String sql) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x = "";
        if(rs.next()){
            x = rs.getString("sPhone");
        }
        return x;
    }
    public int getCurrentRow() throws SQLException{
        return y;
    }
    public int getRowNumber()throws SQLException{
        int no_rows =0;
        rs = stmt.executeQuery("SELECT gName FROM recGroup");
        while(rs.next()){
            no_rows++;
        }
        return no_rows;      
    }
   
    public void insertStatement(String sql) throws SQLException{
        stmt = conn.createStatement();
        stmt.executeUpdate(sql); 
    }
    
    public void deleteStatement(String sql) throws SQLException{
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }
   
  
    public String getString(String sql, String tableName) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        String x ="";
        if(rs.next()){
            x = rs.getString(tableName);
        }
        return x;
    }
    
    public int getInt(String sql, String tableName) throws SQLException{
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        int x = 0;
        if(rs.next()){
            x = rs.getInt(tableName);
        }
        return x;
    }
    
    
    public void resultStatement(String sql) throws SQLException{
        stmt= conn.createStatement();
        rs = stmt.executeQuery(sql);   
    }
    
     public void prepareStmtGroup(String sql, String gN, String gS, int gY, String gG) throws SQLException{
         System.out.println("Putting into group database...");
         ps= conn.prepareStatement(sql);
         ps.setString(1, gN);
         ps.setString(2, gS);
         ps.setInt(3, gY);
         ps.setString(4, gG);
         int rows = ps.executeUpdate();
         resultStatement("Select * from recgroup");
         
     }
     public void prepareStmtStudio(String sql, String sN, String sA, String sO, String sP) throws SQLException{
         System.out.println("Putting into studio database...");
         ps= conn.prepareStatement(sql);
         ps.setString(1, sN);
         ps.setString(2, sA);
         ps.setString(3, sO);
         ps.setString(4, sP);
         int rows = ps.executeUpdate();
         resultStatement("Select * from recstudio");
         
     }
     
     public void prepareStmtAlbum (String sql, String aN, String gN, String sN, String aD, String aL, int aS) throws SQLException{
         System.out.println("Putting into album db....");
         ps = conn.prepareStatement(sql);
         ps.setString(1, aN);
         ps.setString(2, gN);
         ps.setString(3, sN);
         ps.setString(4, aD);
         ps.setString(5, aL);
         ps.setInt(6, aS);
         int rows = ps.executeUpdate();
         resultStatement("Select * from album");
     }
}
    

