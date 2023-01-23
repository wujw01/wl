//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.driver;

import com.goldhuman.xml.xmlobject;
import java.sql.Driver;
import java.sql.DriverManager;

public class handler extends xmlobject {
    public handler() {
    }

    public void action() {
        if(application.handler.debug) {
            System.err.println("Load Driver " + this.name);
        }

        try {
            DriverManager.registerDriver((Driver)Class.forName(this.name).newInstance());
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}
