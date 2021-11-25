package com.company.database;

import com.company.model.Query;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DataBaseWorker implements Serializable {

    private final Connection connection;
    private List<Query> queries;
    private final Saver saver = new Saver();

    public DataBaseWorker(){
        this.queries = new ArrayList<>();
        this.saver.createTable();
        this.connection = saver.getConnect();
    }

    public List<Query> getQueries(){
        return queries;
    }

    public void add(Query query){
        queries.add(query);
        saver.addPoint(query.getX(), query.getY(), query.getR(), query.isHit());
        System.out.println("added");
        System.out.println(queries.size());
    }

    public void clear(){
        saver.clear();
        queries.clear();
    }

    public void getPoints(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM results");
            int cnt = 0;
            while(resultSet.next()){
                ++cnt;
                Query query = new Query();
                query.setX(resultSet.getDouble(1));
                query.setY(resultSet.getDouble(2));
                query.setR(resultSet.getDouble(3));
                query.setHit(resultSet.getBoolean(4));
                this.queries.add(query);
            }
            System.out.println("cnt: " + cnt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
