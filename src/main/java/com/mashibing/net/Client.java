package com.mashibing.net;

import com.mashibing.tank_01.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static final Client INSTANCE=new Client();
    private Channel channel=null;

    private Client(){

    }

    public void connect(){
        EventLoopGroup group=new NioEventLoopGroup(1);
        Bootstrap b=new Bootstrap();

        try{
            ChannelFuture f=b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("localhost",8888);

            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(!future.isSuccess()){
                        System.out.println("not connected!");
                    }else{
                        System.out.println("connected!");
                        channel=future.channel();
                    }
                }
            });

            f.sync();
            f.channel().closeFuture().sync();
            System.out.println("connection closed!");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public void send(Msg msg){
        System.out.println("Send:"+msg);
        channel.writeAndFlush(msg);
    }

    public void closeConnect(){
        //this.send("_bye_");
       /* channel.close();*/
    }

}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new MsgEncoder())
                .addLast(new MsgDecoder())
                .addLast(new ClientHandler());
    }
}

class ClientHandler extends SimpleChannelInboundHandler<Msg>{

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Msg msg) throws Exception {
        System.out.println(msg);
        msg.handle();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));

    }
}