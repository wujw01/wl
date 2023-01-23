//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.table.column;

import com.goldhuman.xml.xmlobject;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private String sql_type;
    private Class java_type;
    private boolean not_null = false;

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.sql_type = var1.getValue("sql-type");

        try {
            this.java_type = Class.forName(var1.getValue("java-type"));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        String var2 = var1.getValue("not-null");
        this.not_null = var2 != null && var2.compareTo("true") == 0;
    }

    public void action() {
    }

    public String toString() {
        return this.name + " " + this.sql_type + (this.not_null?" NOT ":" ") + "NULL";
    }

    public Class type() {
        return this.java_type;
    }
}
