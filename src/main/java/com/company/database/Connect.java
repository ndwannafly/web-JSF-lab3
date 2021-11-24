package com.company.database;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class Connect {
    private DataSource dataSource = null;
    private Connection connection = null;

    protected void setConnection(){
        try{
            InitialContext initialContext = new InitialContext();
            this.dataSource = (DataSource) initialContext.lookup("java:/PostgresDS");
            if(dataSource != null){
                System.out.println("dit con me database day roi!!!!");
                this.connection = dataSource.getConnection();
                System.out.println(this.connection.getClientInfo());
            } else{
                System.out.println("Deo co database");
            }
        } catch (SQLException | NamingException sqlException){
            sqlException.printStackTrace();
        }
    }

    protected Connection getConnection(){
        return this.connection;
    }

    public Connect(){
        setConnection();
    }
}
