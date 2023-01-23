//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.procedure;

import com.goldhuman.xml.xmlobject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private static Map instance = new HashMap();
    private Set conn_set = new HashSet();
    private String operate;
    private application.procedure.parameter.handler[] parameter;
    private String sql_clause;

    public handler() {
    }

    protected void setattr(Attributes attributes) {
        super.setattr(attributes);
        this.conn_set.addAll(Arrays.asList(attributes.getValue("connection").split("[ \n\t,]+")));
        this.operate = attributes.getValue("operate");
        instance.put(this.name, this);
    }

    public void action() {
        xmlobject[] axmlobject = this.parent.children;
        Iterator hashset = this.conn_set.iterator();

        String vector;
        boolean stringbuffer;
        do {
            int l;
            if(!hashset.hasNext()) {
                axmlobject = this.children;
                HashSet var6 = new HashSet();
                Vector var7 = new Vector();

                int var8;
                for(var8 = 0; var8 < axmlobject.length; ++var8) {
                    if(axmlobject[var8] instanceof application.procedure.parameter.handler) {
                        axmlobject[var8].action();
                        if(var6.add(axmlobject[var8].name)) {
                            var7.add(axmlobject[var8]);
                        } else {
                            System.err.println("In Procedure \'" + this.name + "\' Duplicate parameter \'" + axmlobject[var8].name + "\'");
                        }
                    }
                }

                this.parameter = new application.procedure.parameter.handler[var7.size()];

                for(var8 = 0; var8 < var7.size(); ++var8) {
                    this.parameter[var8] = (application.procedure.parameter.handler)var7.get(var8);
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

                StringBuffer var9 = new StringBuffer("{call ");
                var9.append(this.name).append("(");

                for(l = 0; l < this.parameter.length; ++l) {
                    var9.append("?,");
                }

                var9.setCharAt(var9.lastIndexOf(","), ')');
                this.sql_clause = var9.append("}").toString();
                return;
            }

            vector = (String)hashset.next();
            stringbuffer = false;
            if(application.handler.debug) {
                System.err.println("Procedure \'" + this.name + "\' Bind \'" + vector + "\'");
            }

            for(l = 0; l < axmlobject.length; ++l) {
                if(axmlobject[l] instanceof application.connection.handler && axmlobject[l].name.compareTo(vector) == 0) {
                    stringbuffer = true;
                    break;
                }
            }
        } while(stringbuffer);

        System.err.println("In Procedure \'" + this.name + "\' Connection \'" + vector + "\' Miss");
    }

    public String toString() {
        StringBuffer stringbuffer = new StringBuffer("CREATE PROCEDURE ");
        stringbuffer.append(this.name).append("(");

        for(int i = 0; i < this.parameter.length; ++i) {
            if(this.parameter[i].out) {
                stringbuffer.append("out ");
            } else {
                stringbuffer.append("in ");
            }

            stringbuffer.append(this.parameter[i].name).append(" ").append(this.parameter[i].sql_type.toUpperCase());
            stringbuffer.append(", ");
        }

        stringbuffer.deleteCharAt(stringbuffer.lastIndexOf(", "));
        stringbuffer.append(")\n");
        stringbuffer.append("BEGIN\n\t").append(this.content.trim()).append("\nEND");
        return stringbuffer.toString();
    }

    private void Drop() {
        Connection connection;
        for(Iterator iterator = this.conn_set.iterator(); iterator.hasNext(); application.connection.handler.put(connection)) {
            connection = application.connection.handler.get((String)iterator.next());

            try {
                Statement exception = connection.createStatement();
                exception.executeUpdate("DROP PROCEDURE " + this.name);
                exception.close();
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    private void Create() {
        Connection connection;
        for(Iterator iterator = this.conn_set.iterator(); iterator.hasNext(); application.connection.handler.put(connection)) {
            connection = application.connection.handler.get((String)iterator.next());

            try {
                Statement exception = connection.createStatement();
                exception.executeUpdate(this.toString());
                exception.close();
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    public static handler get(String s) {
        return (handler)instance.get(s);
    }

    public int execute(Object[] aobj, String s) throws Exception {
        if(aobj.length != this.parameter.length) {
            throw new SQLException("Parameter number error");
        } else if(s != null && !this.conn_set.contains(s)) {
            throw new SQLException("Connection \'" + s + "\' NOT Match");
        } else {
            boolean flag = false;
            Connection connection = application.connection.handler.get(s);
            CallableStatement callablestatement = null;
            byte i = 0;

            try {
                System.out.println("Prepare procedure call:" + this.sql_clause);
                callablestatement = connection.prepareCall(this.sql_clause);

                int exception2;
                for(exception2 = 0; exception2 < aobj.length; ++exception2) {
                    callablestatement.setObject(exception2 + 1, aobj[exception2]);
                    if(this.parameter[exception2].out) {
                        callablestatement.registerOutParameter(exception2 + 1, this.parameter[exception2].out_type);
                    }
                }

                callablestatement.execute();

                for(exception2 = 0; exception2 < aobj.length; ++exception2) {
                    if(this.parameter[exception2].out) {
                        aobj[exception2] = callablestatement.getObject(exception2 + 1);
                    }
                }
            } catch (Exception var12) {
                throw var12;
            }

            try {
                if(callablestatement != null) {
                    callablestatement.close();
                }
            } catch (Exception var11) {
                ;
            }

            try {
                application.connection.handler.put(connection);
            } catch (Exception var10) {
                ;
            }

            try {
                if(callablestatement != null) {
                    callablestatement.close();
                }
            } catch (Exception var9) {
                ;
            }

            try {
                application.connection.handler.put(connection);
            } catch (Exception var8) {
                ;
            }

            return i;
        }
    }

    public int execute(Object[] aobj) throws Exception {
        return this.execute(aobj, (String)null);
    }
}
