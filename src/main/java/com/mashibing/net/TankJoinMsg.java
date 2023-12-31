package com.mashibing.net;

import com.mashibing.tank_01.Dir;
import com.mashibing.tank_01.Group;
import com.mashibing.tank_01.Tank;
import com.mashibing.tank_01.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankJoinMsg extends Msg{

    public int x,y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;

    public TankJoinMsg() {
    }

    public TankJoinMsg(Tank t){
        this.x=t.getX();
        this.y=t.getY();
        this.dir=t.getDir();
        this.moving=t.isMoving();
        this.group=t.getGruop();
        this.id=t.getId();
    }

    public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }

    @Override
    public void handle() {
        if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId())||
        TankFrame.INSTANCE.findTankByUUID(this.id) != null){
            return;
        }

        Tank t=new Tank(this);

        TankFrame.INSTANCE.addTank(t);

        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }

    @Override
    public byte[] toBytes() {

        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;

        try{
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);

            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());

            dos.flush();

            bytes = baos.toByteArray();
        }catch (Exception e){
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
        DataInputStream dis=null;

        try{
            dis=new DataInputStream(new ByteArrayInputStream(bytes));
            this.x=dis.readInt();
            this.y=dis.readInt();
            this.dir=Dir.values()[dis.readInt()];
            this.moving=dis.readBoolean();
            this.group=Group.values()[dis.readInt()];
            this.id=new UUID(dis.readLong(),dis.readLong());
        }catch (IOException e){
            e.printStackTrace();
        }finally {

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
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append(this.getClass().getName())
                .append("{")
                .append("uuid="+id+"|")
                .append("x="+x+"|")
                .append("y="+y+"|")
                .append("moving="+moving+"|")
                .append("dir="+dir+"|")
                .append("group="+group+"|")
                .append("}");

        return builder.toString();
    }
}
