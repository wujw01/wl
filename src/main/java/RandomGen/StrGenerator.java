//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package RandomGen;

import java.util.Random;

public class StrGenerator {
    private Random rdgen = new Random();
    private static StrGenerator instance = new StrGenerator();

    private StrGenerator() {
    }

    public String Generate_Mix(int var1) {
        byte[] var2 = new byte[var1];
        boolean var3 = false;

        for(int var4 = 0; var4 < var1; ++var4) {
            byte var5;
            do {
                var5 = (byte)(this.rdgen.nextInt() % 64 + 48);
            } while((var5 < 50 || var5 > 57) && (var5 < 65 || var5 > 90 || var5 == 73 || var5 == 79));

            var2[var4] = var5;
        }

        return new String(var2);
    }

    public String Generate_Num(int var1) {
        byte[] var2 = new byte[var1];
        boolean var3 = false;

        for(int var4 = 0; var4 < var1; ++var4) {
            byte var5;
            do {
                var5 = (byte)(this.rdgen.nextInt() % 64 + 48);
            } while(var5 < 48 || var5 > 57);

            var2[var4] = var5;
        }

        return new String(var2);
    }

    public static StrGenerator GetInstance() {
        return instance;
    }
}
