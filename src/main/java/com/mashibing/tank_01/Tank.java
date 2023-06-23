package com.mashibing.tank_01;

import com.mashibing.net.BulletNewMsg;
import com.mashibing.net.Client;
import com.mashibing.net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank {

    private static final int SPEED=2;

    public static int WIDTH=ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT=ResourceMgr.goodTankU.getHeight();

    private UUID id=UUID.randomUUID();

    Rectangle rect=new Rectangle();

    private Random random=new Random();

    private int x,y;

    private Dir dir=Dir.DOWN;

    private boolean moving=false;

    private TankFrame tf=null;

    private boolean living=true;

    private Group gruop=Group.BAD;

    public Tank() {
    }

    public Tank(int x,int y,Dir dir,Group group,TankFrame tf){
        this.x=x;
        this.y=y;
        this.dir=dir;
        this.gruop=group;
        this.tf=tf;

        rect.x=this.x;
        rect.y=this.y;
        rect.width=WIDTH;
        rect.height=HEIGHT;
    }

    public Tank(TankJoinMsg msg){
        this.x=msg.x;
        this.y=msg.y;
        this.dir=msg.dir;
        this.moving=msg.moving;
        this.gruop=msg.group;
        this.id=msg.id;

        rect.x=this.x;
        rect.y=this.y;
        rect.width=WIDTH;
        rect.height=HEIGHT;
    }

    private void boundsCheck(){
        if(this.x<2) x=2;
        if(this.y<28) y=28;
        if(this.x>TankFrame.GAME_WIDTH-Tank.WIDTH-2)
            x=TankFrame.GAME_WIDTH-Tank.WIDTH-2;
        if(this.y>TankFrame.GAME_HEIGHT-Tank.HEIGHT-2)
            y=TankFrame.GAME_HEIGHT-Tank.HEIGHT-2;
    }

    public void die(){
        this.living=false;
        int eX=this.getX()+Tank.WIDTH/2-Explode.WIDTH/2;
        int eY=this.getY()+Tank.HEIGHT/2-Explode.HEIGHT/2;
        TankFrame.INSTANCE.explodes.add(new Explode(eX,eY));
    }

    public void fire(){
        int bX=this.x+Tank.WIDTH/2-Bullet.WIDTH/2;
        int bY=this.y+Tank.HEIGHT/2-Bullet.HEIGHT/2;

        Bullet b=new Bullet(this.id,bX,bY,this.dir,this.gruop,this.tf);

        tf.bullets.add(b);

        Client.INSTANCE.send(new BulletNewMsg(b));

        //if(this.group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start()
    }

    public void move(){
        if(!living) return;
        if(!moving) return;

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

        if(this.gruop == Group.BAD && random.nextInt(100)>95)
            this.fire();

        if(this.gruop == Group.BAD && random.nextInt(100)>95)
            randomDir();

        boundsCheck();

        rect.x=this.x;
        rect.y=this.y;
    }

    public void paint(Graphics g){
        Color c=g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(),this.y,this.y-20);
        g.drawString("live="+living,x,y-20);
        g.setColor(c);

        if(!living){
            moving=false;
            Color cc=g.getColor();
            g.setColor(Color.WHITE);
            g.drawRect(x,y,WIDTH,HEIGHT);
            g.setColor(cc);
            return;
        }

        switch (dir){
            case LEFT:
                g.drawImage(this.gruop == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL,x,y,null);
                break;
            case UP:
                g.drawImage(this.gruop == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(this.gruop == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR,x,y,null);
                break;
            case DOWN:
                g.drawImage(this.gruop == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD,x,y,null);
                break;
            default:
                break;
        }

        move();
    }

    private void randomDir(){
        this.dir=Dir.values()[random.nextInt(4)];
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
        this.tf = tf;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public Group getGruop() {
        return gruop;
    }

    public void setGruop(Group gruop) {
        this.gruop = gruop;
    }

    /*private int x,y;
    public static int WIDTH=ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT=ResourceMgr.goodTankU.getHeight();
    private static final int SPEED=3;
    private boolean moving=true;
    private TankFrame tf=null;
    private Dir dir=Dir.DOWN;
    private Group group=Group.BAD;
    private Random random=new Random();
    Rectangle rectangleTank=new Rectangle();
    private boolean living=true;
    public UUID id=UUID.randomUUID();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame  tf){

        this.x=x;
        this.y=y;
        this.dir=dir;
        this.group=group;
        this.tf=tf;

        rectangleTank.x=this.x;
        rectangleTank.y=this.y;
        rectangleTank.width=WIDTH;
        rectangleTank.height=HEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    //tank的画笔
    public void paint(Graphics graphics){

        if(!living){
            tf.tanks.remove(this);
        }

        switch (dir){
            case LEFT:
                graphics.drawImage( this.group == Group.GOOD ? ResourceMgr.goodTankL:ResourceMgr.badTankL,x,y,null);
                break;
            case UP:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU:ResourceMgr.badTankU,x,y,null);
                break;
            case RIGHT:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR:ResourceMgr.badTankR,x,y,null);
                break;
            case DOWN:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD:ResourceMgr.badTankD,x,y,null);
                break;
            default:
                break;
        }
        move();
    }

    private void move(){
        if(!moving){
            return;
        }

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

        //敌方坦克随机开火
        if(this.group == Group.BAD && random.nextInt(100)>95){
            this.fire();
        }

        //敌方坦克随机走动
        if(this.group == Group.BAD && random.nextInt(100)>95){
            randomDir();
        }

        //间距碰撞
        boundsCheck();

        //更新rect
        rectangleTank.x=this.x;
        rectangleTank.y=this.y;
    }

    //坦克不越界
    private void boundsCheck(){
        if(this.x<2) {
            x=2;
        }

        if(this.y<28) {
            y=28;
        }

        if(this.x>TankFrame.GAME_WIDTH-Tank.WIDTH-2) {
           x=TankFrame.GAME_WIDTH-Tank.WIDTH-2;
        }

        if(this.y>TankFrame.GAME_HEIGHT-Tank.HEIGHT-2) {
            y=TankFrame.GAME_HEIGHT-Tank.HEIGHT-2;
        }
    }

    private void randomDir() {
        this.dir=Dir.values()[random.nextInt(4)];
    }

    public void fire(){

        int bX=this.x+Tank.WIDTH/2-Bullet.WIDTH/2;
        int bY=this.y+Tank.HEIGHT/2-Bullet.HEIGHT/2;
        tf.bullets.add(new Bullet(bX,bY,this.dir,this.group,this.tf));
    }

    //坦克消亡
    public void die() {
        this.living=false;
    }*/
}
