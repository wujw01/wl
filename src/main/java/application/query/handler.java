//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.query;

import com.goldhuman.xml.xmlobject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private static Map instance = new HashMap();
    private Map select_map = new HashMap();
    public application.query.column.handler[] column;
    public application.query.table.handler[] table;

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        instance.put(this.name, this);
    }

    public void action() {
        xmlobject[] var1 = this.children;
        Vector var2 = new Vector();

        int var3;
        for(var3 = 0; var3 < var1.length; ++var3) {
            if(var1[var3] instanceof application.query.table.handler) {
                var1[var3].action();
                var2.add(var1[var3]);
            }
        }

        this.table = new application.query.table.handler[var2.size()];

        for(var3 = 0; var3 < var2.size(); ++var3) {
            this.table[var3] = (application.query.table.handler)var2.get(var3);
        }

        var2.clear();

        for(var3 = 0; var3 < var1.length; ++var3) {
            if(var1[var3] instanceof application.query.column.handler) {
                var1[var3].action();
                var2.add(var1[var3]);
            }
        }

        this.column = new application.query.column.handler[var2.size()];

        for(var3 = 0; var3 < var2.size(); ++var3) {
            this.column[var3] = (application.query.column.handler)var2.get(var3);
        }

        for(var3 = 0; var3 < var1.length; ++var3) {
            if(var1[var3] instanceof application.query.select.handler) {
                var1[var3].action();
                if(this.select_map.put(var1[var3].name, var1[var3]) != null) {
                    System.err.println("In Query \'" + this.name + "\' Duplicate select \'" + var1[var3].name + "\'");
                }
            }
        }

        if(application.handler.debug) {
            System.err.println(this);
        }

    }

    public String toString() {
        StringBuffer var1 = new StringBuffer();
        Iterator var2 = this.select_map.entrySet().iterator();

        while(var2.hasNext()) {
            var1.append((application.query.select.handler)((Entry)var2.next()).getValue()).append("\n");
        }

        return var1.toString();
    }

    public Object[] executeQuery(String var1, Object[] var2, Object[] var3, String var4) throws Exception {
        if(var3.length != var2.length) {
            throw new SQLException("Parameter number error");
        } else {
            Connection var5 = application.connection.handler.get(var4);
            PreparedStatement var6 = null;
            ResultSet var7 = null;
            Vector var8 = new Vector();

            try {
                var6 = var5.prepareStatement(var1);

                for(int var9 = 0; var9 < var3.length; ++var9) {
                    var6.setObject(var9 + 1, var3[var9]);
                }

                var7 = var6.executeQuery();

                while(var7.next()) {
                    Object[] var23 = new Object[this.column.length];

                    for(int var10 = 0; var10 < this.column.length; ++var10) {
                        var23[var10] = var7.getObject(var10 + 1);
                    }

                    var8.add(var23);
                }

                return var8.toArray();
            } catch (Exception var21) {
                throw var21;
            } finally {
                try {
                    if(var7 != null) {
                        var7.close();
                    }
                } catch (Exception var20) {
                    ;
                }

                try {
                    if(var6 != null) {
                        var6.close();
                    }
                } catch (Exception var19) {
                    ;
                }

                application.connection.handler.put(var5);
            }
        }
    }

    public application.query.select.handler select(String var1) {
        return (application.query.select.handler)this.select_map.get(var1);
    }

    public static handler get(String var0) {
        return (handler)instance.get(var0);
    }
}
