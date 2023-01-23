//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.table.delete;

import com.goldhuman.xml.xmlobject;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private String condition;
    private String sql_clause;
    private Object[] parameter;

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.condition = var1.getValue("condition");
    }

    public void action() {
        int var1 = this.condition.length();
        int var2 = 0;

        for(int var3 = 0; var3 < var1; ++var3) {
            if(this.condition.charAt(var3) == 63) {
                ++var2;
            }
        }

        this.parameter = new Object[var2];
        this.sql_clause = "DELETE FROM " + this.parent.name + " " + this.condition;
    }

    public String toString() {
        return this.sql_clause;
    }

    public int execute(Object[] var1, String var2) throws Exception {
        return ((application.table.handler)this.parent).executeUpdate(this.sql_clause, this.parameter, var1, var2);
    }

    public int execute(Object[] var1) throws Exception {
        return this.execute(var1, (String)null);
    }
}
