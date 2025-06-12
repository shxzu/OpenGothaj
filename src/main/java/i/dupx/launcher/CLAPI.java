package i.dupx.launcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

public class CLAPI {
    private static final long USER_CACHE_EXPIRE_TIME = 60000L;
    private static final Map<String, CLUserInfo> userInfoCache = new HashMap<String, CLUserInfo>();
    private static final CLUserInfo loadingUserInfo = new CLUserInfo();
    private static String lastServer = null;
    private static boolean privacyMode = System.getProperty("clprivacy", "false").equals("true");
    private static IClient client;
    private static List<byte[]> packetQueue;
    private static Set<String> friends;
    private static BiConsumer<Integer, DataInputStream> internalDataProcessor;

    static {
        packetQueue = new LinkedList<byte[]>();
        friends = new HashSet<String>();
        new Thread(CLAPI::cleanupCache).start();
        new Thread(CLAPI::clapi_reader).start();
    }

    public static void setClassAcceptor(BiConsumer<Integer, DataInputStream> internalDataProcessor) {
        CLAPI.internalDataProcessor = internalDataProcessor;
    }

    public static void setClient(IClient client) {
        CLAPI.client = client;
    }

    public static Map<String, CLUserInfo> getUserInfoCache() {
        return Collections.unmodifiableMap(userInfoCache);
    }

    public static boolean getPrivacyMode() {
        return privacyMode;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void setPrivacyMode(boolean privacyMode) {
        CLAPI.privacyMode = privacyMode;
        try {
            Throwable throwable = null;
            Object var2_4 = null;
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    try (DataOutputStream dos = new DataOutputStream(bos);){
                        dos.writeByte(5);
                        dos.writeBoolean(privacyMode);
                        CLAPI.sendPacket(bos.toByteArray());
                    }
                    if (bos == null) return;
                }
                catch (Throwable throwable2) {
                    if (throwable == null) {
                        throwable = throwable2;
                    } else if (throwable != throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    if (bos == null) throw throwable;
                    bos.close();
                    throw throwable;
                }
                bos.close();
                return;
            }
            catch (Throwable throwable3) {
                if (throwable == null) {
                    throwable = throwable3;
                    throw throwable;
                } else {
                    if (throwable == throwable3) throw throwable;
                    throwable.addSuppressed(throwable3);
                }
                throw throwable;
            }
        }
        catch (Throwable t) {
            System.err.println("Failed to send setPrivacyMode(" + privacyMode + ")");
            t.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isFriend(String username) {
        Set<String> set = friends;
        synchronized (set) {
            return friends.contains(username);
        }
    }

    public static String getCLUsername() {
        return System.getProperty("clname", "");
    }

    public static String getCLToken() {
        return System.getProperty("cltoken", "");
    }

    public static int getClientRole() {
        return Integer.parseInt(System.getProperty("clrole", ""));
    }

    public static long getFirstBuyStamp() {
        return Long.parseLong(System.getProperty("clfirstBuy", "0"));
    }

    public static String getBranch() {
        return System.getProperty("clbranch", "");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void reportUsername(String username, String server) {
        Object object;
        if (!Objects.equals(server, lastServer)) {
            object = userInfoCache;
            synchronized (object) {
                userInfoCache.clear();
            }
            lastServer = server;
        }
        try {
            object = null;
            Object var3_5 = null;
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    try (DataOutputStream dos = new DataOutputStream(bos);){
                        dos.writeByte(2);
                        dos.writeBoolean(username != null);
                        if (username != null) {
                            CLAPI.writeString(dos, username);
                        }
                        dos.writeBoolean(server != null);
                        if (server != null) {
                            CLAPI.writeString(dos, server);
                        }
                        CLAPI.sendPacket(bos.toByteArray());
                    }
                    if (bos == null) return;
                }
                catch (Throwable throwable) {
                    if (object == null) {
                        object = throwable;
                    } else if (object != throwable) {
                        ((Throwable)object).addSuppressed(throwable);
                    }
                    if (bos == null) throw object;
                    bos.close();
                    throw object;
                }
                bos.close();
                return;
            }
            catch (Throwable throwable) {
                if (object == null) {
                    object = throwable;
                    throw object;
                } else {
                    if (object == throwable) throw object;
                    ((Throwable)object).addSuppressed(throwable);
                }
                throw object;
            }
        }
        catch (Throwable t) {
            System.err.println("Failed to send reportUsername('" + username + "', '" + server + "')");
            t.printStackTrace();
        }
    }

    public static String getCapeUrl(String username) {
        return "https://api.haze.yt:8443/getCape?token=" + CLAPI.getCLToken() + "&username=" + URLEncoder.encode(username);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static CLUserInfo getUserInfo(String username) {
        Map<String, CLUserInfo> map = userInfoCache;
        synchronized (map) {
            CLUserInfo userData = userInfoCache.get(username);
            if (userData != null) return userData;
            userData = loadingUserInfo;
            userInfoCache.put(username, userData);
            try {
                Throwable throwable = null;
                Object var4_6 = null;
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        try (DataOutputStream dos = new DataOutputStream(bos);){
                            dos.writeByte(3);
                            CLAPI.writeString(dos, username);
                            CLAPI.sendPacket(bos.toByteArray());
                        }
                        if (bos == null) return userData;
                    }
                    catch (Throwable throwable2) {
                        if (throwable == null) {
                            throwable = throwable2;
                        } else if (throwable != throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        if (bos == null) throw throwable;
                        bos.close();
                        throw throwable;
                    }
                    bos.close();
                    {
                    }
                }
                catch (Throwable throwable3) {
                    if (throwable == null) {
                        throwable = throwable3;
                        throw throwable;
                    } else {
                        if (throwable == throwable3) throw throwable;
                        throwable.addSuppressed(throwable3);
                    }
                    throw throwable;
                }
            }
            catch (Throwable t) {
                System.err.println("Failed to send setPrivacyMode(" + privacyMode + ")");
            }
            return userData;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void cleanupCache() {
        Thread.currentThread().setName("CLAPI Cleanup Cache");
        while (true) {
            try {
                try {
                    long time = System.currentTimeMillis();
                    Map<String, CLUserInfo> map = userInfoCache;
                    synchronized (map) {
                        userInfoCache.values().removeIf(userInfo -> time > ((CLUserInfo)userInfo).expire);
                        continue;
                    }
                }
                catch (Throwable t) {
                    t.printStackTrace();
                    try {
                        Thread.sleep(30000L);
                    }
                    catch (InterruptedException interruptedException) {}
                    continue;
                }
            }
            finally {
                try {
                    Thread.sleep(30000L);
                }
                catch (InterruptedException interruptedException) {}
                continue;
            }
            break;
        }
    }

    private static void clapi_reader() {
        Thread.currentThread().setName("CLAPI CL Connection");
        try {
            Throwable throwable = null;
            Object var1_3 = null;
            try (Socket socket = new Socket("127.0.0.1", Integer.parseInt(System.getProperty("clport", "0")));){
                DataInputStream dataInput = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutput = new DataOutputStream(socket.getOutputStream());
                new Thread(() -> CLAPI.clapi_writer(dataOutput)).start();
                while (socket.isConnected() && !socket.isClosed()) {
                    byte[] data = CLAPI.readByteArray(dataInput);
                    try {
                        Throwable throwable2 = null;
                        Object var7_13 = null;
                        try (DataInputStream packetStream = new DataInputStream(new ByteArrayInputStream(data));){
                            CLAPI.readPacket(packetStream.readUnsignedByte(), packetStream);
                        }
                        catch (Throwable throwable3) {
                            if (throwable2 == null) {
                                throwable2 = throwable3;
                            } else if (throwable2 != throwable3) {
                                throwable2.addSuppressed(throwable3);
                            }
                            throw throwable2;
                        }
                    }
                    catch (SocketException se) {
                        break;
                    }
                    catch (Throwable t) {
                        System.err.println("Failed to read packet data");
                        t.printStackTrace();
                    }
                }
            }
            catch (Throwable throwable4) {
                if (throwable == null) {
                    throwable = throwable4;
                } else if (throwable != throwable4) {
                    throwable.addSuppressed(throwable4);
                }
                throw throwable;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void clapi_writer(DataOutputStream output) {
        Thread.currentThread().setName("CLAPI CL Connection");
        while (true) {
            try {
                while (true) {
                    byte[] packet;
                    List<byte[]> list = packetQueue;
                    synchronized (list) {
                        packet = packetQueue.isEmpty() ? null : packetQueue.remove(0);
                    }
                    if (packet == null) {
                        Thread.sleep(1L);
                        continue;
                    }
                    output.writeInt(packet.length);
                    output.write(packet);
                    output.flush();
                }
            }
            catch (Throwable t) {
                System.err.println("Failed to write packet data");
                t.printStackTrace();
                continue;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void sendPacket(byte[] data) {
        List<byte[]> list = packetQueue;
        synchronized (list) {
            packetQueue.add(data);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void sendError(String error) throws IOException {
        Throwable throwable = null;
        Object var2_3 = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                try (DataOutputStream dos = new DataOutputStream(bos);){
                    dos.writeByte(7);
                    CLAPI.writeString(dos, error);
                    CLAPI.sendPacket(bos.toByteArray());
                }
                if (bos == null) return;
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                if (bos == null) throw throwable;
                bos.close();
                throw throwable;
            }
            bos.close();
            return;
        }
        catch (Throwable throwable3) {
            if (throwable == null) {
                throwable = throwable3;
                throw throwable;
            } else {
                if (throwable == throwable3) throw throwable;
                throwable.addSuppressed(throwable3);
            }
            throw throwable;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static void readPacket(int packetType, DataInputStream input) throws IOException {
        switch (packetType) {
            case 0: {
                if (client != null) {
                    client.loadCurrentConfig(CLAPI.readString(input));
                    return;
                }
                CLAPI.sendError("Client = null");
                return;
            }
            case 1: {
                if (client == null) {
                    CLAPI.sendError("Client = null");
                    return;
                }
                Throwable throwable = null;
                Object var3_6 = null;
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        try (DataOutputStream dos = new DataOutputStream(bos);){
                            dos.writeByte(1);
                            dos.writeInt(input.readInt());
                            CLAPI.writeString(dos, client.writeCurrentConfig());
                            CLAPI.sendPacket(bos.toByteArray());
                        }
                        if (bos == null) return;
                    }
                    catch (Throwable throwable2) {
                        if (throwable == null) {
                            throwable = throwable2;
                        } else if (throwable != throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        if (bos == null) throw throwable;
                        bos.close();
                        throw throwable;
                    }
                    bos.close();
                    return;
                }
                catch (Throwable throwable3) {
                    if (throwable == null) {
                        throwable = throwable3;
                        throw throwable;
                    }
                    if (throwable == throwable3) throw throwable;
                    throwable.addSuppressed(throwable3);
                    throw throwable;
                }
            }
            case 3: {
                Map<String, CLUserInfo> map = userInfoCache;
                synchronized (map) {
                    userInfoCache.put(CLAPI.readString(input), new CLUserInfo(input));
                    return;
                }
            }
            case 4: {
                Set<String> set = friends;
                synchronized (set) {
                    friends.clear();
                    String[] stringArray = CLAPI.readStringArray(input);
                    int n = stringArray.length;
                    int n2 = 0;
                    while (true) {
                        if (n2 >= n) {
                            return;
                        }
                        String added = stringArray[n2];
                        friends.add(added);
                        ++n2;
                    }
                }
            }
            case 5: {
                privacyMode = input.readBoolean();
                return;
            }
            case 6: {
                if (client != null) {
                    client.joinServer(CLAPI.readString(input));
                    return;
                }
                CLAPI.sendError("Client = null");
                return;
            }
        }
        internalDataProcessor.accept(packetType, input);
    }

    protected static String[] readStringArray(DataInputStream input) throws IOException {
        String[] strings = new String[input.readUnsignedShort()];
        int i = 0;
        while (i < strings.length) {
            strings[i] = CLAPI.readString(input);
            ++i;
        }
        return strings;
    }

    public static byte[] readByteArray(DataInputStream input) throws IOException {
        byte[] bytes = new byte[input.readInt()];
        input.readFully(bytes);
        return bytes;
    }

    protected static void writeString(DataOutputStream output, String str) throws IOException {
        CLAPI.writeByteArray(output, str.getBytes("UTF-8"));
    }

    protected static void writeByteArray(DataOutputStream output, byte[] bytes) throws IOException {
        output.writeInt(bytes.length);
        output.write(bytes);
    }

    protected static String readString(DataInputStream input) throws IOException {
        return new String(CLAPI.readByteArray(input), "UTF-8");
    }

    public static class CLUserInfo {
        public final String nickname;
        public final int role;
        public final String runningClient;
        public final String runningBranch;
        public final String mc_name;
        private long expire;

        private CLUserInfo(DataInputStream dis) throws IOException {
            if (dis.readBoolean()) {
                this.nickname = CLAPI.readString(dis);
                this.role = dis.readUnsignedByte();
                this.runningClient = CLAPI.readString(dis);
                this.runningBranch = CLAPI.readString(dis);
                this.mc_name = CLAPI.readString(dis);
                this.expire = System.currentTimeMillis() + 60000L;
            } else {
                this.nickname = "None";
                this.role = Integer.MAX_VALUE;
                this.runningClient = "-";
                this.runningBranch = "-";
                this.mc_name = "-";
            }
        }

        private CLUserInfo() {
            this.nickname = "Loading...";
            this.role = Integer.MAX_VALUE;
            this.runningClient = "None";
            this.runningBranch = "None";
            this.expire = Long.MAX_VALUE;
            this.mc_name = "Unknown";
        }

        public String toString() {
            return "nickname=" + this.nickname + "," + "role=" + this.role + "," + "runningClient=" + this.runningClient + "," + "runningBranch=" + this.runningBranch;
        }
    }

    public static interface IClient {
        public String writeCurrentConfig();

        public void loadCurrentConfig(String var1);

        public void joinServer(String var1);
    }
}
