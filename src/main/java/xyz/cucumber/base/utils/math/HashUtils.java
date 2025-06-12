package xyz.cucumber.base.utils.math;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import xyz.cucumber.base.utils.math.Sha256;

public class HashUtils {
    public static String SHA256(String input) {
        String result = "";
        byte[] byteData = Sha256.hash(input.getBytes(Charset.forName("UTF-8")));
        StringBuffer hexString = new StringBuffer();
        int i = 0;
        while (i < byteData.length) {
            String hex = Integer.toHexString(0xFF & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
            ++i;
        }
        result = hexString.toString();
        return result;
    }

    public static String MD5(String string) {
        try {
            byte[] msgBytes = string.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(msgBytes);
            byte[] resultByte = md.digest();
            BigInteger bigInt = new BigInteger(1, resultByte);
            String res = bigInt.toString(16);
            while (res.length() < 32) {
                res = "0" + res;
            }
            return res;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
