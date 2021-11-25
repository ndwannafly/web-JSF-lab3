package com.company.model;

import java.io.Serializable;

public class Query implements Serializable {
    private double x;
    private double y;
    private double r;
    private boolean hit;

    public Query(){
    }

    public Query(double x, double y, double r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Query(double x, double y, double r, boolean hit){
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }

    public double getR() {
        return r;
    }

    public boolean isHit() {
        return hit;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setHit(boolean result){
        this.hit = result;
    }

    public void checkHit(){
        System.out.println("check new hit: ");
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("r: " + r);
        if(x >= -r/2 && x <= 0 && y <= 0 && y >= -r) setHit(true);
        else if( x*x + y*y <= r*r && x <= r && x >= 0 && y <= 0 && y >= -r) setHit(true);
        else if (x <= 0 && x >= -r && y >= 0 && y <= r && x >= y - r) setHit(true);
        else setHit(false);
    }
}
