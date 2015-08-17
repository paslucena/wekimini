/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekimini;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
/**
 *
 * @author pasl
 */
public class MySQLConnect {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    final private String host = "igor.gold.ac.uk:3307";
    final private String user = "psilv001";
    final private String passwd = "123456";
    
    public int login(String name, String pass) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
          .getConnection("jdbc:mysql://" + host + "/psilv001_wekistore"
              + "?user=" + user + "&password=" + passwd );
            
             preparedStatement = connect
          .prepareStatement("select userID from users where username = ? and password = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            int id = 0;
            while(resultSet.next()){
                id = resultSet.getInt("userID");
            }
            System.out.println(id);
            
            return id;
        }catch(Exception e){
            throw e;
        }finally{
            close();
        }
    }
    
    public void signup (String name, String pass) throws ClassNotFoundException, SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager
          .getConnection("jdbc:mysql://" + host + "/psilv001_wekistore"
              + "?user=" + user + "&password=" + passwd );
            
            preparedStatement = connect.prepareStatement("select username from users where username = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            
            if (!resultSet.next() ) {
                preparedStatement = connect
                .prepareStatement("insert into users values (default, ?, ?)");
                  preparedStatement.setString(1, name);
                  preparedStatement.setString(2, pass);
                  preparedStatement.executeUpdate();
            }else{
                 throw new NullPointerException();
            }
       
        }catch(ClassNotFoundException e){
            e.printStackTrace();
            throw e;
        }catch(SQLException e){
            e.printStackTrace();
            throw e;
        }catch(NullPointerException e){
            throw e;
        }finally{
            close();
        }
        
    }
    
    public void insert (String name, String desc, int id, String type, String ctgry, String envmt) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://" + host + "/psilv001_wekistore"+ "?user=" + user + "&password=" + passwd );
 
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            int downloads = 0;

            preparedStatement = connect.prepareStatement("insert into cards values (default, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, desc);
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setInt(4, id);
            preparedStatement.setInt(5, downloads);
            preparedStatement.setString(6, type);
            preparedStatement.setString(7, ctgry);
            preparedStatement.setString(8, envmt);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            close();
        }
    }
    
    private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  

}
    
}

