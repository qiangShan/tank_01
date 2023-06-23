package com.mashibing.tank_01;

import java.awt.*;

public class Explode {

    public static int WIDTH=ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT=ResourceMgr.explodes[0].getHeight();

    private int x,y;

    private int step=0;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g){
        g.drawImage(ResourceMgr.explodes[step++],x,y,null);
        if(step>=ResourceMgr.explodes.length){
            TankFrame.INSTANCE.explodes.remove(this);
        }
    }

    /*private int x,y;
    public static int WIDTH=ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT=ResourceMgr.explodes[0].getHeight();
    private TankFrame tf=null;
    private int step=0;


    public Explode(int x,int y,TankFrame tf){
        this.x=x;
        this.y=y;
        this.tf=tf;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics graphics){
        graphics.drawImage(ResourceMgr.explodes[step++],x,y,null);
        if(step >= ResourceMgr.explodes.length){
            tf.explodes.remove(this);
        }
    }*/
}
