package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.math.BigInteger;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.login.server.S03PacketEnableCompression;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginClient
implements INetHandlerLoginClient {
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    private final GuiScreen previousGuiScreen;
    private final NetworkManager networkManager;
    private GameProfile gameProfile;

    public NetHandlerLoginClient(NetworkManager networkManagerIn, Minecraft mcIn, GuiScreen p_i45059_3_) {
        this.networkManager = networkManagerIn;
        this.mc = mcIn;
        this.previousGuiScreen = p_i45059_3_;
    }

    @Override
    public void handleEncryptionRequest(S01PacketEncryptionRequest packetIn) {
        final SecretKey secretkey = CryptManager.createNewSharedKey();
        String s = packetIn.getServerId();
        PublicKey publickey = packetIn.getPublicKey();
        String s1 = new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey)).toString(16);
        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().isOnLAN()) {
            try {
                this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
            }
            catch (AuthenticationException var10) {
                logger.warn("Couldn't connect to auth servers but will continue to join LAN");
            }
        } else {
            try {
                this.getSessionService().joinServer(this.mc.getSession().getProfile(), this.mc.getSession().getToken(), s1);
            }
            catch (AuthenticationUnavailableException var7) {
                this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0])));
                return;
            }
            catch (InvalidCredentialsException var8) {
                this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0])));
                return;
            }
            catch (AuthenticationException authenticationexception) {
                this.networkManager.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", authenticationexception.getMessage()));
                return;
            }
        }
        this.networkManager.sendPacket(new C01PacketEncryptionResponse(secretkey, publickey, packetIn.getVerifyToken()), (GenericFutureListener<? extends Future<? super Void>>)new GenericFutureListener<Future<? super Void>>(){

            public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
                NetHandlerLoginClient.this.networkManager.enableEncryption(secretkey);
            }
        }, new GenericFutureListener[0]);
    }

    private MinecraftSessionService getSessionService() {
        return this.mc.getSessionService();
    }

    @Override
    public void handleLoginSuccess(S02PacketLoginSuccess packetIn) {
        this.gameProfile = packetIn.getProfile();
        this.networkManager.setConnectionState(EnumConnectionState.PLAY);
        this.networkManager.setNetHandler(new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
    }

    @Override
    public void onDisconnect(IChatComponent reason) {
        this.mc.displayGuiScreen(new GuiDisconnected(this.previousGuiScreen, "connect.failed", reason));
    }

    @Override
    public void handleDisconnect(S00PacketDisconnect packetIn) {
        this.networkManager.closeChannel(packetIn.func_149603_c());
    }

    @Override
    public void handleEnableCompression(S03PacketEnableCompression packetIn) {
        if (!this.networkManager.isLocalChannel()) {
            this.networkManager.setCompressionTreshold(packetIn.getCompressionTreshold());
        }
    }
}
