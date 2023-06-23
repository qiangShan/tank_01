package com.mashibing.tank_01;

import com.mashibing.net.Client;
import org.junit.Test;

public class Main {

    public static void main(String[] args) {
        TankFrame tf=TankFrame.INSTANCE;
        tf.setVisible(true);


        new Thread(()->{
            while(true){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        Client.INSTANCE.connect();
    }

    /*@Test
    public void test01() {
        TankFrame tf=TankFrame.INSTANCE;
        tf.setVisible(true);


        new Thread(()->{
            while(true){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        Client.INSTANCE.connect();

        //初始化敌方坦克
        for(int i=0;i<5;i++){
            tf.tanks.add(new Tank(50*i+100,200,Dir.DOWN,Group.BAD,tf));
        }

        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tf.repaint();
        }*//*
    }*/
}
