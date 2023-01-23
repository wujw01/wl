//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.query.select;

import com.goldhuman.xml.xmlobject;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private String sql_clause;
    private String condition;
    private boolean unique;
    public Object[] parameter;
    private application.query.select.cache.handler cache;

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.condition = var1.getValue("condition");
        String var2 = var1.getValue("unique");
        this.unique = var2 != null && var2.compareTo("true") == 0;
    }

    public void action() {
        application.query.column.handler[] var1 = ((application.query.handler)this.parent).column;
        application.query.table.handler[] var2 = ((application.query.handler)this.parent).table;
        StringBuffer var3 = new StringBuffer("SELECT ");
        if(this.unique) {
            var3.append("DISTINCT ");
        }

        int var4;
        for(var4 = 0; var4 < var1.length; ++var4) {
            var3.append(var1[var4].compute == null?var1[var4].canonical_name:var1[var4].compute).append(" AS ").append(var1[var4].name).append(',');
        }

        var3.setCharAt(var3.lastIndexOf(","), ' ');
        var3.append("FROM ");

        for(var4 = 0; var4 < var2.length; ++var4) {
            var3.append(var2[var4].name);
            if(var2[var4].alias != null) {
                var3.append(' ').append(var2[var4].alias);
            }

            var3.append(',');
        }

        var3.setCharAt(var3.lastIndexOf(","), ' ');
        var4 = 0;
        int var6;
        if(this.condition != null) {
            var3.append(this.condition);
            int var5 = this.condition.length();

            for(var6 = 0; var6 < var5; ++var6) {
                if(this.condition.charAt(var6) == 63) {
                    ++var4;
                }
            }
        }

        this.parameter = new Object[var4];
        this.sql_clause = var3.toString();
        xmlobject[] var7 = this.children;

        for(var6 = 0; var6 < var7.length; ++var6) {
            if(var7[var6] instanceof application.query.select.cache.handler) {
                var7[var6].action();
                this.cache = (application.query.select.cache.handler)var7[var6];
                break;
            }
        }

    }

    public String toString() {
        return this.sql_clause;
    }

    public Object[] execute(Object[] var1, String var2) throws Exception {
        Object[] var3;
        if(this.cache != null && (var3 = this.cache.search(var1)) != null) {
            return var3;
        } else {
            var3 = ((application.query.handler)this.parent).executeQuery(this.sql_clause, this.parameter, var1, var2);
            if(this.cache != null) {
                this.cache.append(var1, var3);
            }

            return var3;
        }
    }

    public Object[] execute(Object[] var1) throws Exception {
        return this.execute(var1, (String)null);
    }
}
