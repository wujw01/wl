//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.table.insert;

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
        if(this.condition == null) {
            application.table.column.handler[] var1 = ((application.table.handler)this.parent).column;
            StringBuffer var2 = new StringBuffer("(");
            StringBuffer var3 = new StringBuffer("(");
            this.parameter = new Object[var1.length];

            for(int var4 = 0; var4 < var1.length; ++var4) {
                var2.append(var1[var4].name).append(',');
                var3.append("?,");
            }

            var2.setCharAt(var2.lastIndexOf(","), ')');
            var3.setCharAt(var3.lastIndexOf(","), ')');
            this.sql_clause = "INSERT INTO " + this.parent.name + " " + var2 + " VALUES " + var3;
        } else {
            int var5 = this.condition.length();
            int var6 = 0;

            for(int var7 = 0; var7 < var5; ++var7) {
                if(this.condition.charAt(var7) == 63) {
                    ++var6;
                }
            }

            this.parameter = new Object[var6];
            this.sql_clause = "INSERT INTO " + this.parent.name + " " + this.condition;
        }

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
