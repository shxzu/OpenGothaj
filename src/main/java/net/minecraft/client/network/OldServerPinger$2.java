package net.minecraft.client.network;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

class OldServerPinger$2
extends ChannelInitializer<Channel> {
    private final ServerAddress val$serveraddress;
    private final ServerData val$server;

    OldServerPinger$2(ServerAddress serverAddress, ServerData serverData) {
        this.val$serveraddress = serverAddress;
        this.val$server = serverData;
    }

    protected void initChannel(Channel p_initChannel_1_) throws Exception {
        try {
            p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
        }
        catch (ChannelException channelException) {
            // empty catch block
        }
        p_initChannel_1_.pipeline().addLast(new ChannelHandler[]{new SimpleChannelInboundHandler<ByteBuf>(){

            public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {
                super.channelActive(p_channelActive_1_);
                ByteBuf bytebuf = Unpooled.buffer();
                try {
                    bytebuf.writeByte(254);
                    bytebuf.writeByte(1);
                    bytebuf.writeByte(250);
                    char[] achar = "MC|PingHost".toCharArray();
                    bytebuf.writeShort(achar.length);
                    char[] cArray = achar;
                    int n = achar.length;
                    int n2 = 0;
                    while (n2 < n) {
                        char c0 = cArray[n2];
                        bytebuf.writeChar((int)c0);
                        ++n2;
                    }
                    bytebuf.writeShort(7 + 2 * val$serveraddress.getIP().length());
                    bytebuf.writeByte(127);
                    achar = val$serveraddress.getIP().toCharArray();
                    bytebuf.writeShort(achar.length);
                    cArray = achar;
                    n = achar.length;
                    n2 = 0;
                    while (n2 < n) {
                        char c1 = cArray[n2];
                        bytebuf.writeChar((int)c1);
                        ++n2;
                    }
                    bytebuf.writeInt(val$serveraddress.getPort());
                    p_channelActive_1_.channel().writeAndFlush((Object)bytebuf).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                }
                finally {
                    bytebuf.release();
                }
            }

            protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, ByteBuf p_channelRead0_2_) throws Exception {
                short short1 = p_channelRead0_2_.readUnsignedByte();
                if (short1 == 255) {
                    String s = new String(p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() * 2).array(), Charsets.UTF_16BE);
                    String[] astring = Iterables.toArray(PING_RESPONSE_SPLITTER.split(s), String.class);
                    if ("§1".equals(astring[0])) {
                        int i = MathHelper.parseIntWithDefault(astring[1], 0);
                        String s1 = astring[2];
                        String s2 = astring[3];
                        int j = MathHelper.parseIntWithDefault(astring[4], -1);
                        int k = MathHelper.parseIntWithDefault(astring[5], -1);
                        val$server.version = -1;
                        val$server.gameVersion = s1;
                        val$server.serverMOTD = s2;
                        val$server.populationInfo = (Object)((Object)EnumChatFormatting.GRAY) + j + (Object)((Object)EnumChatFormatting.DARK_GRAY) + "/" + (Object)((Object)EnumChatFormatting.GRAY) + k;
                    }
                }
                p_channelRead0_1_.close();
            }

            public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
                p_exceptionCaught_1_.close();
            }
        }});
    }
}
