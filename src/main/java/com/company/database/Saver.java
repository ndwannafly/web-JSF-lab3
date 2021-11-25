package com.company.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Saver {
    private Connection connection = null;
    private final Connect connect = new Connect();

    protected void init(){
        connection = connect.getConnection();
    }

    public void createTable(){
        init();
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS results(x float, y float , r float, res boolean)");
            statement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public void addPoint(double x, double y, double r, boolean result){
        try{
            if(connection == null){
                createTable();
            }
            PreparedStatement statement = connection.prepareStatement("INSERT INTO results(x,y,r,res) values(?, ?, ?, ?)");
            statement.setDouble(1, x);
            statement.setDouble(2, y);
            statement.setDouble(3, r);
            statement.setBoolean(4, result);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public Connection getConnect(){
        return this.connection;
    }

    public void clear(){
        if(connection == null) createTable();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM results");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
