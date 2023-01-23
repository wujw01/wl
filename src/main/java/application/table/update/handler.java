//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.table.update;

import com.goldhuman.xml.xmlobject;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private String condition;
    private String column_name;
    private String sql_clause;
    private Object[] parameter;
    private application.table.column.handler[] column;

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.column_name = var1.getValue("column");
        this.condition = var1.getValue("condition");
    }

    public void action() {
        int var1 = 0;
        StringBuffer var2 = new StringBuffer("UPDATE ");
        var2.append(this.parent.name).append(" SET ");
        application.table.column.handler[] var3 = ((application.table.handler)this.parent).column;
        String[] var4 = this.column_name.split("[ \n\t,]+");
        this.column_name = null;
        this.column = new application.table.column.handler[var4.length];

        int var5;
        int var6;
        label39:
        for(var5 = 0; var5 < var4.length; ++var5) {
            for(var6 = 0; var6 < var3.length; ++var6) {
                if(var3[var6].name.compareTo(var4[var5]) == 0) {
                    this.column[var1++] = var3[var6];
                    var2.append(var4[var5]).append("=?,");
                    continue label39;
                }
            }

            System.err.println("UPDATE \'" + this.name + "\' REF \'" + this.parent.name + "." + var4[var5] + "\' Miss");
        }

        var2.setCharAt(var2.lastIndexOf(","), ' ');
        if(this.condition != null) {
            var2.append(this.condition);
            var5 = this.condition.length();

            for(var6 = 0; var6 < var5; ++var6) {
                if(this.condition.charAt(var6) == 63) {
                    ++var1;
                }
            }
        }

        this.parameter = new Object[var1];
        this.sql_clause = var2.toString();
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
