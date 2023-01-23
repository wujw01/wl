import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Test {

    public static byte[] StringPassword(String s)
    {
        byte[] abyte0 = new byte[16];
        for (int i = 1; i <= 16; i++) {
            abyte0[(i - 1)] = ((byte)Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
        }
        return abyte0;
    }

    public static void main(String args[]) throws NoSuchAlgorithmException {
        String name = "1";
        String password = "1";
//        StringPassword(s);
        MessageDigest messagedigest = MessageDigest.getInstance("MD5");
        messagedigest.update(name.toLowerCase().getBytes());
        messagedigest.update(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder("0x");
        StringBuilder sb2 = new StringBuilder();
        byte[] abyte0 = messagedigest.digest();
        char[] c = new char[abyte0.length];
        for(int i = 0 ; i < abyte0.length; i++){
            int v = abyte0[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            sb2.append(abyte0[i]);
        }
        System.out.println(stringBuilder);
        System.out.println(sb2);
        byte[] abyte1 = StringPassword(sb2.toString());
        for(int n = 0; n < abyte1.length; ++n) {
            System.out.println("base:" + abyte0[n] + "; code:" + abyte1[n]);
            if(abyte0[n] != abyte1[n]) {
                System.out.println("error");
            }
        }
    }

}
