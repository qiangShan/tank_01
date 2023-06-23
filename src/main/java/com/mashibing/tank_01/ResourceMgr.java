package com.mashibing.tank_01;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMgr {

    public static BufferedImage goodTankL,goodTankU,goodTankR,goodTankD;
    public static BufferedImage badTankL,badTankU,badTankR,badTankD;
    public static BufferedImage bulletL,bulletU,bulletR,bulletD;
    public static BufferedImage[] explodes=new BufferedImage[16];

    static {
        try{
            //主战坦克
            goodTankU= ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank2.png"));
            goodTankL=ImageUtil.rotateImage(goodTankU,-90);
            goodTankR=ImageUtil.rotateImage(goodTankU,90);
            goodTankD=ImageUtil.rotateImage(goodTankU,180);

            //敌方坦克
            badTankU= ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            badTankL=ImageUtil.rotateImage(badTankU,-90);
            badTankR=ImageUtil.rotateImage(badTankU,90);
            badTankD=ImageUtil.rotateImage(badTankU,180);

            //子弹
            bulletU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            bulletL=ImageUtil.rotateImage(bulletU,-90);
            bulletR=ImageUtil.rotateImage(bulletU,90);
            bulletD=ImageUtil.rotateImage(bulletU,180);

            //爆炸
            for(int i=0; i<16; i++){
                explodes[i]=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e"+(i+1)+".gif"));
            }

        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
