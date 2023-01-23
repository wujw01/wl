//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.table;

import com.goldhuman.xml.xmlobject;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private static Map instance = new HashMap();
    private Set conn_set = new HashSet();
    private String operate;
    private Map delete_map = new HashMap();
    private Map insert_map = new HashMap();
    private Map update_map = new HashMap();
    public application.table.column.handler[] column;
    public application.table.primarykey.handler primarykey;
    public application.table.index.handler[] index;
    public Vector keys = new Vector();

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.conn_set.addAll(Arrays.asList(var1.getValue("connection").split("[ \n\t,]+")));
        this.operate = var1.getValue("operate");
        instance.put(this.name, this);
    }

    public void action() {
        xmlobject[] var1 = this.parent.children;
        Iterator var2 = this.conn_set.iterator();

        String var3;
        boolean var4;
        do {
            if(!var2.hasNext()) {
                var1 = this.children;
                HashSet var9 = new HashSet();
                Vector var10 = new Vector();

                int var11;
                for(var11 = 0; var11 < var1.length; ++var11) {
                    if(var1[var11] instanceof application.table.column.handler) {
                        var1[var11].action();
                        if(var9.add(var1[var11].name)) {
                            var10.add(var1[var11]);
                        } else {
                            System.err.println("In Table \'" + this.name + "\' Duplicate column \'" + var1[var11].name + "\'");
                        }
                    }
                }

                this.column = new application.table.column.handler[var10.size()];

                for(var11 = 0; var11 < var10.size(); ++var11) {
                    this.column[var11] = (application.table.column.handler)var10.get(var11);
                }

                for(var11 = 0; var11 < var1.length; ++var11) {
                    if(var1[var11] instanceof application.table.primarykey.handler) {
                        var1[var11].action();
                        this.primarykey = (application.table.primarykey.handler)var1[var11];
                        break;
                    }
                }

                var9.clear();
                var10.clear();

                for(var11 = 0; var11 < var1.length; ++var11) {
                    if(var1[var11] instanceof application.table.index.handler) {
                        var1[var11].action();
                        if(var9.add(var1[var11].name)) {
                            var10.add(var1[var11]);
                        } else {
                            System.err.println("In Table \'" + this.name + "\' Duplicate index \'" + var1[var11].name + "\'");
                        }
                    }
                }

                this.index = new application.table.index.handler[var10.size()];

                for(var11 = 0; var11 < var10.size(); ++var11) {
                    this.index[var11] = (application.table.index.handler)var10.get(var11);
                }

                for(var11 = 0; var11 < var1.length; ++var11) {
                    if(var1[var11] instanceof application.table.insert.handler) {
                        var1[var11].action();
                        if(this.insert_map.put(var1[var11].name, var1[var11]) != null) {
                            System.err.println("In Table \'" + this.name + "\' Duplicate insert \'" + var1[var11].name + "\'");
                        }
                    }
                }

                for(var11 = 0; var11 < var1.length; ++var11) {
                    if(var1[var11] instanceof application.table.delete.handler) {
                        var1[var11].action();
                        if(this.delete_map.put(var1[var11].name, var1[var11]) != null) {
                            System.err.println("In Table \'" + this.name + "\' Duplicate delete \'" + var1[var11].name + "\'");
                        }
                    }
                }

                for(var11 = 0; var11 < var1.length; ++var11) {
                    if(var1[var11] instanceof application.table.update.handler) {
                        var1[var11].action();
                        if(this.update_map.put(var1[var11].name, var1[var11]) != null) {
                            System.err.println("In Table \'" + this.name + "\' Duplicate update \'" + var1[var11].name + "\'");
                        }
                    }
                }

                if(this.primarykey != null) {
                    this.keys.add(this.primarykey.column);
                }

                if(this.index != null) {
                    for(var11 = 0; var11 < this.index.length; ++var11) {
                        if(this.index[var11].unique) {
                            this.keys.add(this.index[var11].column);
                        }
                    }
                }

                Collections.sort(this.keys, new Comparator() {
                    public int compare(Object var1, Object var2) {
                        return Array.getLength(var1) - Array.getLength(var2);
                    }
                });
                LinkedList var13 = new LinkedList(this.keys);
                this.keys.clear();

                application.table.column.handler[] var12;
                label136:
                for(; !var13.isEmpty(); this.keys.add(var12)) {
                    var12 = (application.table.column.handler[])((application.table.column.handler[])var13.removeFirst());
                    ListIterator var6 = var13.listIterator();

                    while(true) {
                        label131:
                        while(true) {
                            if(!var6.hasNext()) {
                                continue label136;
                            }

                            application.table.column.handler[] var7 = (application.table.column.handler[])((application.table.column.handler[])var6.next());

                            for(int var8 = 0; var8 < var12.length; ++var8) {
                                if(var12[var8] != var7[var8]) {
                                    continue label131;
                                }
                            }

                            var6.remove();
                        }
                    }
                }

                if(application.handler.debug) {
                    System.err.println(this);
                }

                if(this.operate != null) {
                    if(this.operate.compareTo("create") == 0) {
                        this.Create();
                    }

                    if(this.operate.compareTo("drop") == 0) {
                        this.Drop();
                    }

                    if(this.operate.compareTo("replace") == 0) {
                        this.Drop();
                        this.Create();
                    }
                }

                return;
            }

            var3 = (String)var2.next();
            var4 = false;
            if(application.handler.debug) {
                System.err.println("Table \'" + this.name + "\' Bind \'" + var3 + "\'");
            }

            for(int var5 = 0; var5 < var1.length; ++var5) {
                if(var1[var5] instanceof application.connection.handler && var1[var5].name.compareTo(var3) == 0) {
                    var4 = true;
                    break;
                }
            }
        } while(var4);

        System.err.println("In Table \'" + this.name + "\' Connection \'" + var3 + "\' Miss");
    }

    private String DDLTable() {
        StringBuffer var1 = new StringBuffer("CREATE TABLE " + this.name + " (\n");

        for(int var2 = 0; var2 < this.column.length; ++var2) {
            var1.append('\t').append(this.column[var2]).append(",\n");
        }

        if(this.primarykey != null) {
            var1.append('\t').append(this.primarykey).append(",\n");
        }

        return var1.deleteCharAt(var1.lastIndexOf(",")).append(")\n").toString();
    }

    @Override
    public String toString() {
        StringBuffer var1 = new StringBuffer(this.DDLTable());

        int var2;
        for(var2 = 0; var2 < this.index.length; ++var2) {
            var1.append(this.index[var2]);
        }

        for(var2 = 0; var2 < this.keys.size(); ++var2) {
            application.table.column.handler[] var3 = (application.table.column.handler[])((application.table.column.handler[])this.keys.get(var2));
            var1.append("Key[").append(var2).append("]:");

            for(int var4 = 0; var4 < var3.length; ++var4) {
                var1.append(' ').append(var3[var4].name);
            }

            var1.append('\n');
        }

        Iterator var5 = this.insert_map.entrySet().iterator();

        while(var5.hasNext()) {
            var1.append((application.table.insert.handler)((Entry)var5.next()).getValue()).append("\n");
        }

        var5 = this.delete_map.entrySet().iterator();

        while(var5.hasNext()) {
            var1.append((application.table.delete.handler)((Entry)var5.next()).getValue()).append("\n");
        }

        var5 = this.update_map.entrySet().iterator();

        while(var5.hasNext()) {
            var1.append((application.table.update.handler)((Entry)var5.next()).getValue()).append("\n");
        }

        return var1.toString();
    }

    private void Drop() {
        Connection var2;
        for(Iterator var1 = this.conn_set.iterator(); var1.hasNext(); application.connection.handler.put(var2)) {
            var2 = application.connection.handler.get((String)var1.next());

            try {
                Statement var3 = var2.createStatement();
                var3.executeUpdate("DROP TABLE " + this.name);
                var3.close();
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    private void Create() {
        Connection var2;
        for(Iterator var1 = this.conn_set.iterator(); var1.hasNext(); application.connection.handler.put(var2)) {
            var2 = application.connection.handler.get((String)var1.next());

            try {
                Statement var3 = var2.createStatement();
                var3.addBatch(this.DDLTable());

                for(int var4 = 0; var4 < this.index.length; ++var4) {
                    var3.addBatch(this.index[var4].toString());
                }

                var3.executeBatch();
                var3.close();
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

    }

    public int executeUpdate(String var1, Object[] var2, Object[] var3, String var4) throws Exception {
        if(var3.length != var2.length) {
            throw new SQLException("Parameter number error");
        } else if(var4 != null && !this.conn_set.contains(var4)) {
            throw new SQLException("Connection \'" + var4 + "\' NOT Match");
        } else {
            Connection var5 = application.connection.handler.get(var4);
            PreparedStatement var6 = null;
            boolean var7 = true;

            try {
                var6 = var5.prepareStatement(var1);

                for(int var8 = 0; var8 < var3.length; ++var8) {
                    var6.setObject(var8 + 1, var3[var8]);
                }

                int var18 = var6.executeUpdate();
                return var18;
            } catch (Exception var16) {
                throw var16;
            } finally {
                try {
                    if(var6 != null) {
                        var6.close();
                    }
                } catch (Exception var15) {
                    ;
                }

                application.connection.handler.put(var5);
            }
        }
    }

    public application.table.delete.handler delete(String var1) {
        return (application.table.delete.handler)this.delete_map.get(var1);
    }

    public application.table.insert.handler insert(String var1) {
        return (application.table.insert.handler)this.insert_map.get(var1);
    }

    public application.table.update.handler update(String var1) {
        return (application.table.update.handler)this.update_map.get(var1);
    }

    public static handler get(String var0) {
        return (handler)instance.get(var0);
    }
}
