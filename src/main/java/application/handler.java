//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application;

import com.goldhuman.xml.xmlobject;
import java.util.HashSet;
import java.util.Vector;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    public static boolean debug;

    public handler() {
    }

    private void traverse(Class var1) {
        xmlobject[] var2 = this.children;
        HashSet var3 = new HashSet();
        new Vector();

        for(int var5 = 0; var5 < var2.length; ++var5) {
            if(var1.isInstance(var2[var5])) {
                if(var3.add(var2[var5].name)) {
                    var2[var5].action();
                } else {
                    System.err.println("Duplicate " + var1.getName() + " " + var2[var5].name);
                }
            }
        }

    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        String var2 = var1.getValue("debug");
        debug = var2 != null && var2.compareTo("true") == 0;
    }

    public void action() {
        this.traverse(application.driver.handler.class);
        this.traverse(application.connection.handler.class);
        this.traverse(application.table.handler.class);
        this.traverse(application.query.handler.class);
        this.traverse(application.procedure.handler.class);
    }
}
