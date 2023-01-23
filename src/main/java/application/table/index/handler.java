//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.table.index;

import com.goldhuman.xml.xmlobject;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private String column_name;
    public boolean unique = false;
    public application.table.column.handler[] column;

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.column_name = var1.getValue("column");
        String var2 = var1.getValue("unique");
        if(var2 != null && var2.compareTo("true") == 0) {
            this.unique = true;
        }

    }

    public void action() {
        String[] var1 = this.column_name.split("[ \n\t,]+");
        this.column_name = null;
        this.column = new application.table.column.handler[var1.length];
        application.table.column.handler[] var2 = ((application.table.handler)this.parent).column;

        label24:
        for(int var3 = 0; var3 < var1.length; ++var3) {
            for(int var4 = 0; var4 < var2.length; ++var4) {
                if(var2[var4].name.compareTo(var1[var3]) == 0) {
                    this.column[var3] = var2[var4];
                    continue label24;
                }
            }

            System.err.println("INDEX \'" + this.name + "\' REF \'" + this.parent.name + "." + var1[var3] + "\' Miss");
        }

    }

    public String toString() {
        StringBuffer var1 = new StringBuffer("CREATE " + (this.unique?"UNIQUE ":"") + "INDEX " + this.name + " ON " + this.parent.name + " (");

        for(int var2 = 0; var2 < this.column.length; ++var2) {
            var1.append(this.column[var2].name).append(',');
        }

        return var1.deleteCharAt(var1.lastIndexOf(",")).append(")\n").toString();
    }
}
