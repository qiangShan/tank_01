package com.mashibing.tank_01;

import com.mashibing.net.Client;
import com.mashibing.net.TankDieMsg;

import java.awt.*;
import java.util.UUID;

public class Bullet {

    private static final int SPEED=6;

    public static int WIDTH=ResourceMgr.bulletD.getWidth();

    public static int HEIGHT=ResourceMgr.bulletD.getHeight();

    private UUID id= UUID.randomUUID();
    private UUID playerId;

    Rectangle rect=new Rectangle();

    private int x,y;

    private Dir dir;

    private boolean living=true;

    TankFrame tf=null;

    private Group group=Group.BAD;

    public Bullet(UUID playerId, int x, int y, Dir dir, Group group, TankFrame tf){
        this.playerId=playerId;
        this.x=x;
        this.y=y;
        this.dir=dir;
        this.group=group;
        this.tf=tf;

        rect.x=this.x;
        rect.y=this.y;
        rect.width=WIDTH;
        rect.height=HEIGHT;
    }

    public void collideWith(Tank tank){
        if(this.playerId.equals(tank.getId()))
            return;
        if(this.living && tank.isLiving() && this.rect.intersects(tank.rect)){
            tank.die();
            this.die();
            Client.INSTANCE.send(new TankDieMsg(this.id,tank.getId()));
        }
    }

    public void die(){
        this.living=false;
    }

    public void move(){

        switch (dir){
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }

        rect.x=this.x;
        rect.y=this.y;

        if(x<0 || y<0 || x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT)
            living=false;

    }

    public void paint(Graphics g){
        if(!living){
            tf.bullets.remove(this);
        }

        switch (dir){
            case LEFT:
                g.drawImage(ResourceMgr.bulletL,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
            default:
                break;
        }

        move();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
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

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
        this.tf = tf;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    /*private static final int SPEED=10;
    public static int WIDTH=ResourceMgr.bulletD.getWidth();
    public static int HEIGHT=ResourceMgr.bulletD.getHeight();
    private boolean living=true;
    private int x,y;
    private Dir dir;
    private TankFrame tf=null;
    private Group group=Group.BAD;
    Rectangle rectangleBullet=new Rectangle();

    public Bullet(int x,int y,Dir dir,Group group,TankFrame tf){
        this.x=x;
        this.y=y;
        this.dir=dir;
        this.group=group;
        this.tf=tf;

        rectangleBullet.x=this.x;
        rectangleBullet.y=this.y;
        rectangleBullet.width=WIDTH;
        rectangleBullet.height=HEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics graphics){

        if(!living){
            tf.bullets.remove(this);
        }

        switch (dir){
            case LEFT:
             graphics.drawImage(ResourceMgr.bulletL,x,y,null);
             break;
            case UP:
                graphics.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
            case DOWN:
                graphics.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
            default:
                break;
        }

        move();
    }

    private void move() {

        switch (dir){
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }

        rectangleBullet.x=this.x;
        rectangleBullet.y=this.y;

        if(x<0 || y<0 || x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT){
            living=false;
        }
    }

    public void collidWith(Tank tank){

        if(this.group == tank.getGroup()) {
            return;
        }

        if(rectangleBullet.intersects(tank.rectangleTank)){
            tank.die();
            this.die();
            int eX=tank.getX()+Tank.WIDTH/2-Explode.WIDTH/2;
            int eY=tank.getY()+Tank.HEIGHT/2-Explode.HEIGHT/2;
            tf.explodes.add(new Explode(eX,eY,tf));
        }
    }

    //子弹消亡
    private void die() {
        this.living=false;
    }*/
}
