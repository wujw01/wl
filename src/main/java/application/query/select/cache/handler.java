//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.query.select.cache;

import com.goldhuman.Common.Cache;
import com.goldhuman.Common.Cache.Item;
import com.goldhuman.xml.xmlobject;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private static String DEFAULT_KEY = "DEFAULT";
    private int size;
    private int timeout;
    public Cache cache;

    public handler() {
        this.size = Cache.default_size;
        this.timeout = Cache.default_timeout;
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        String var2 = var1.getValue("size");
        if(var2 != null) {
            this.size = Integer.parseInt(var2);
        }

        var2 = var1.getValue("timeout");
        if(var2 != null) {
            this.timeout = Integer.parseInt(var2);
        }

    }

    public void action() {
        Object[] var1 = ((application.query.select.handler)this.parent).parameter;
        int var2 = var1.length;
        if(var2 == 0) {
            var2 = 1;
        }

        int[] var3 = new int[var2];

        for(int var4 = 0; var4 < var2; var3[var4] = var4++) {
            ;
        }

        this.cache = Cache.Create(this.parent.parent.name + "_" + this.parent.name, var2 + 1, var3, this.size, this.timeout);
    }

    public Object[] search(Object[] var1) {
        try {
            Item var2 = this.cache.newItem();
            if(var1.length <= 0) {
                return (Object[])((Object[])this.cache.find(var2.set(0, DEFAULT_KEY)).get(1));
            } else {
                for(int var3 = 0; var3 < var1.length; ++var3) {
                    var2.set(var3, var1[var3]);
                }

                return (Object[])((Object[])this.cache.find(var2).get(var1.length));
            }
        } catch (Exception var4) {
            return null;
        }
    }

    public void append(Object[] var1, Object var2) {
        try {
            Item var3 = this.cache.newItem();
            if(var1.length > 0) {
                for(int var4 = 0; var4 < var1.length; ++var4) {
                    var3.set(var4, var1[var4]);
                }

                var3.set(var1.length, var2).commit();
            } else {
                var3.set(0, DEFAULT_KEY).set(1, var2).commit();
            }
        } catch (Exception var5) {
            ;
        }

    }
}
