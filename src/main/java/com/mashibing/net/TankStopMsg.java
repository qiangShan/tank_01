package com.mashibing.net;

import com.mashibing.tank_01.Tank;
import com.mashibing.tank_01.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankStopMsg extends Msg{

    UUID id;
    int x,y;

    public TankStopMsg(){

    }

    public TankStopMsg(Tank t){
        this.id=t.getId();
        this.x=t.getX();
        this.y=t.getY();
    }

    public TankStopMsg(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())){
            return;
        }

        Tank t=TankFrame.INSTANCE.findTankByUUID(this.id);

        if(t != null){
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
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
        return MsgType.TankStop;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append("[")
                .append("uuid=" + id + " | ")
                .append("x=" + x + " | ")
                .append("y=" + y + " | ")
                .append("]");
        return builder.toString();
    }
}
