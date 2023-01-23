//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.connection;

import com.goldhuman.xml.xmlobject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private static Map pools;
    private String url;
    private String username;
    private String password;
    private int initSize;

    public handler() {
    }

    public int getInitSize() {
        return this.initSize;
    }

    public static Connection get(String var0) {
        return var0 == null?((handler.SimplePool)((Entry)pools.entrySet().iterator().next()).getValue()).getConnection():((handler.SimplePool)pools.get(var0)).getConnection();
    }

    public static Connection get() {
        return get((String)null);
    }

    public static void put(Connection var0) {
        try {
            Iterator var1 = pools.entrySet().iterator();

            while(var1.hasNext()) {
                ((handler.SimplePool)((Entry)var1.next()).getValue()).returnConnection(var0);
            }
        } catch (Exception var2) {
            ;
        }

    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.url = var1.getValue("url");
        this.username = var1.getValue("username");
        this.password = var1.getValue("password");
        String var2 = var1.getValue("poolsize");
        this.initSize = var2 == null?10:Integer.parseInt(var2);
    }

    public void action() {
        try {
            if(application.handler.debug) {
                System.err.print("Connect to " + this.url);
            }

            pools.put(this.name, new handler.SimplePool(this.initSize, (String)null, this.url, this.username, this.password));
            if(application.handler.debug) {
                System.err.println("pool of " + this.name + " init successed");
            }

            if(application.handler.debug) {
                System.err.println();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    static {
        (new Timer()).schedule(new handler.CheckTask(), 600000L, 600000L);
        pools = new HashMap();
    }

    class SimplePool {
        private handler.SimplePool.Dummy[] pool = null;
        private int size;
        String connurl;
        String usrname;
        String pwd;

        SimplePool(int var2, String var3, String var4, String var5, String var6) {
            this.size = var2;
            this.connurl = var4;
            handler.this.username = var5;
            this.pwd = var6;

            try {
                this.pool = new handler.SimplePool.Dummy[this.size];
                if(var3 != null) {
                    Class.forName(var3);
                }

                for(int var7 = 0; var7 < this.size; ++var7) {
                    try {
                        this.pool[var7] = new handler.SimplePool.Dummy();
                        this.pool[var7].conn = DriverManager.getConnection(this.connurl, handler.this.username, this.pwd);
                        this.pool[var7].isValid = true;
                    } catch (Exception var9) {
                        ;
                    }
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            }

        }

        public synchronized void doCheck() {
            for(int var1 = 0; var1 < this.size; ++var1) {
                try {
                    this.pool[var1].isValid = !this.pool[var1].conn.isClosed();
                } catch (Exception var4) {
                    this.pool[var1].isValid = false;
                }

                if(!this.pool[var1].isValid) {
                    try {
                        this.pool[var1].conn = DriverManager.getConnection(this.connurl, handler.this.username, this.pwd);
                        this.pool[var1].isValid = true;
                        this.notifyAll();
                    } catch (Exception var3) {
                        ;
                    }
                }
            }

        }

        private Connection _getConnection() {
            for(int var1 = 0; var1 < this.size; ++var1) {
                if(this.pool[var1].isValid && !this.pool[var1].isActive) {
                    this.pool[var1].isActive = true;
                    return this.pool[var1].conn;
                }
            }

            return null;
        }

        public synchronized Connection getConnection() {
            Connection var1 = null;

            while((var1 = this._getConnection()) == null) {
                try {
                    this.wait();
                } catch (Exception var3) {
                    ;
                }
            }

            return var1;
        }

        public synchronized void returnConnection(Connection var1) {
            for(int var2 = 0; var2 < this.size; ++var2) {
                if(this.pool[var2].conn == var1) {
                    this.pool[var2].isActive = false;

                    try {
                        this.notifyAll();
                    } catch (Exception var4) {
                        ;
                    }

                    return;
                }
            }

        }

        private class Dummy {
            Connection conn;
            boolean isActive;
            boolean isValid;

            private Dummy() {
                this.isActive = false;
                this.isValid = false;
            }
        }
    }

    static class CheckTask extends TimerTask {
        CheckTask() {
        }

        public void run() {
            Iterator var1 = handler.pools.entrySet().iterator();

            while(var1.hasNext()) {
                handler.SimplePool var2 = (handler.SimplePool)((Entry)var1.next()).getValue();
                var2.doCheck();
            }

        }
    }
}
