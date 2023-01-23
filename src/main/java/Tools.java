import com.goldhuman.account.storage;
import com.goldhuman.xml.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public class Tools {

    public static void main(String args[]) throws FileNotFoundException {
        parser.parse(new FileInputStream(Tools.class.getResource("table.xml").getPath()));
        storage.addUser("s0","s1","s2","s3",
                "s4","s5","s6","s7","s8","s9","s10",
                "s11","s12", 0 ,"20151111","s14","s15");

    }

}
