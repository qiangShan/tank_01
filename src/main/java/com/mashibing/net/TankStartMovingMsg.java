package com.mashibing.net;

import com.mashibing.tank_01.Dir;
import com.mashibing.tank_01.Tank;
import com.mashibing.tank_01.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankStartMovingMsg extends Msg{

    UUID id;
    int x,y;
    Dir dir;

    public TankStartMovingMsg(){

    }

    public TankStartMovingMsg(UUID id,int x,int y,Dir dir){
        this.id=id;
        this.x=x;
        this.y=y;
        this.dir=dir;
    }

    public TankStartMovingMsg(Tank tank){
        this.id=tank.getId();
        this.x=tank.getX();
        this.y=tank.getY();
        this.dir=tank.getDir();
    }

    @Override
    public void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())){
            return;
        }

        Tank t=TankFrame.INSTANCE.findTankByUUID(this.id);

        if(t != null){
            t.setMoving(true);
            t.setX(this.x);
            t.setY(this.y);
            t.setDir(this.dir);
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;

        try{
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.flush();
            bytes= baos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(baos != null){
                    baos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try{
                if(dos != null){
                    dos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bais=null;
        DataInputStream dis=null;

        try{
            bais=new ByteArrayInputStream(bytes);
            dis=new DataInputStream(bais);
            this.id=new UUID(dis.readLong(),dis.readLong());
            this.x=dis.readInt();
            this.y=dis.readInt();
            this.dir=Dir.values()[dis.readInt()];
        }catch (IOException e){
            e.printStackTrace();
        }finally {

            try{
                if(bais != null){
                    bais.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            try{
                if(dis != null){
                    dis.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStartMoving;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + " | ")
                .append("x=" + x + " | ")
                .append("y=" + y + " | ")
                .append("dir=" + dir + " | ")
                .append("]");
        return builder.toString();
    }
}
