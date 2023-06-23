package com.mashibing.net;

import com.mashibing.tank_01.Bullet;
import com.mashibing.tank_01.Dir;
import com.mashibing.tank_01.Group;
import com.mashibing.tank_01.TankFrame;

import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg{

    UUID playerID;
    UUID id;
    int x,y;
    Dir dir;
    Group group;

    public BulletNewMsg() {
    }

    public BulletNewMsg(Bullet bullet){
        this.playerID=bullet.getPlayerId();
        this.id=bullet.getId();
        this.x=bullet.getX();
        this.y=bullet.getY();
        this.dir=bullet.getDir();
        this.group=bullet.getGroup();
    }

    public BulletNewMsg(UUID playerID, UUID id, int x, int y, Dir dir, Group group) {
        this.playerID = playerID;
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    @Override
    public void handle() {
        if(this.playerID.equals(TankFrame.INSTANCE.getMainTank().getId())){
            return;
        }

        Bullet bullet=new Bullet(this.playerID,x,y,dir,group,TankFrame.INSTANCE);
        bullet.setId(this.id);
        TankFrame.INSTANCE.addBullet(bullet);
    }

    @Override
    public byte[] toBytes() {

        ByteArrayOutputStream baos=null;
        DataOutputStream dos=null;
        byte[] bytes=null;

        try{
            baos=new ByteArrayOutputStream();
            dos=new DataOutputStream(baos);
            dos.writeLong(playerID.getMostSignificantBits());
            dos.writeLong(playerID.getLeastSignificantBits());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
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

            this.playerID=new UUID(dis.readLong(),dis.readLong());
            this.id=new UUID(dis.readLong(),dis.readLong());
            this.x=dis.readInt();
            this.y=dis.readInt();
            this.dir=Dir.values()[dis.readInt()];
            this.group=Group.values()[dis.readInt()];


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
        return MsgType.BulletNew;
    }
}
