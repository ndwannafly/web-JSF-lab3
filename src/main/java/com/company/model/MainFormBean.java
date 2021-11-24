package com.company.model;


import com.company.database.DataBaseWorker;

import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@ManagedBean(name = "mainFormBean")
@SessionScoped

public class MainFormBean implements Serializable {

    private Query newQuery;
    private DataBaseWorker dataBaseWorker;

    public MainFormBean(){
        System.out.println("create new form bean!");
        this.newQuery = new Query();
        this.dataBaseWorker = new DataBaseWorker();
        dataBaseWorker.getPoints();
    }

    public MainFormBean(Query newQuery, DataBaseWorker dataBaseWorker){
        this.newQuery = newQuery;
        this.dataBaseWorker = dataBaseWorker;
    }

    public DataBaseWorker getDataBaseWorker(){
        return this.dataBaseWorker;
    }

    public void setDataBaseWorker(DataBaseWorker dataBaseWorker){
        this.dataBaseWorker = dataBaseWorker;
    }

    public Query getNewQuery(){
        return this.newQuery;
    }

    public void setNewQuery(Query query){
        this.newQuery = query;
    }

    public String submit(){
        newQuery.checkHit();
        if(validate(newQuery)) {
            Query queryForTheList = new Query(newQuery.getX(), newQuery.getY(), newQuery.getR(), newQuery.isHit());
            this.dataBaseWorker.add(queryForTheList);
        }
        //return "result.xhtml?faces-redirect=true";
        return "query.xhtml?faces-redirect=true";
    }

    public String clear(){
        this.dataBaseWorker.clear();
        return "query.xhtml?faces-redirect=true";
    }

    public String backToHome(){
        return "query";
    }

    public boolean validate(Query query){
        return query.getX() >= -4 && query.getX() <= 4 && query.getY() >= -5 && query.getY() <= 5 &&
                query.getR() >= 1 && query.getR() <= 3;
    }
}
