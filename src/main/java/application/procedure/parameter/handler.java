//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.procedure.parameter;

import com.goldhuman.xml.xmlobject;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    public String sql_type;
    public Class java_type;
    public boolean in;
    public boolean out;
    public int out_type = 0;

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.sql_type = var1.getValue("sql-type");

        try {
            this.java_type = Class.forName(var1.getValue("java-type"));
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        String var2 = var1.getValue("in");
        this.in = var2 != null && var2.compareTo("true") == 0;
        var2 = var1.getValue("out");
        this.out = var2 != null && var2.compareTo("true") == 0;

        try {
            this.out_type = Class.forName("java.sql.Types").getField(this.sql_type.split("[\\W]+")[0].toUpperCase()).getInt((Object)null);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void action() {
    }
}
