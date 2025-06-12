package net.minecraft.client.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import net.minecraft.client.network.LanServerDetector;

public class LanServerDetector$ThreadLanServerFind
extends Thread {
    private final LanServerDetector.LanServerList localServerList;
    private final InetAddress broadcastAddress;
    private final MulticastSocket socket;

    public LanServerDetector$ThreadLanServerFind(LanServerDetector.LanServerList p_i1320_1_) throws IOException {
        super("LanServerDetector #" + field_148551_a.incrementAndGet());
        this.localServerList = p_i1320_1_;
        this.setDaemon(true);
        this.socket = new MulticastSocket(4445);
        this.broadcastAddress = InetAddress.getByName("224.0.2.60");
        this.socket.setSoTimeout(5000);
        this.socket.joinGroup(this.broadcastAddress);
    }

    @Override
    public void run() {
        byte[] abyte = new byte[1024];
        while (!this.isInterrupted()) {
            DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length);
            try {
                this.socket.receive(datagrampacket);
            }
            catch (SocketTimeoutException var5) {
                continue;
            }
            catch (IOException ioexception) {
                logger.error("Couldn't ping server", (Throwable)ioexception);
                break;
            }
            String s = new String(datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength());
            logger.debug(datagrampacket.getAddress() + ": " + s);
            this.localServerList.func_77551_a(s, datagrampacket.getAddress());
        }
        try {
            this.socket.leaveGroup(this.broadcastAddress);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.socket.close();
    }
}
