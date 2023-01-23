//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.query.column;

import com.goldhuman.xml.xmlobject;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    private String column_name;
    public String canonical_name;
    public String compute;
    public Class java_type;
    public application.table.column.handler column;

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.column_name = var1.getValue("column");
        this.compute = var1.getValue("compute");

        try {
            this.java_type = Class.forName(var1.getValue("java-type"));
        } catch (Exception var3) {
            ;
        }

    }

    public void action() {
        if(this.compute != null) {
            if(this.java_type == null) {
                System.err.println("In Query \'" + this.parent.name + "\' Compute Column \'" + this.compute + "\' MUST Have java-type");
            }

        } else {
            String[] var1 = this.column_name.split("[\\.]+");
            application.query.table.handler[] var2 = ((application.query.handler)this.parent).table;
            application.table.column.handler[] var3 = null;
            int var4;
            label85:
            switch(var1.length) {
                case 1:
                    var4 = 0;

                    while(true) {
                        if(var4 >= var2.length) {
                            break label85;
                        }

                        var3 = var2[var4].table.column;

                        for(int var5 = 0; var5 < var3.length; ++var5) {
                            if(var3[var5].name.compareTo(var1[0]) == 0) {
                                this.column = var3[var5];
                                this.canonical_name = (var2[var4].alias != null?var2[var4].alias:var2[var4].name) + "." + var1[0];
                                break label85;
                            }
                        }

                        ++var4;
                    }
                case 2:
                    for(var4 = 0; var4 < var2.length; ++var4) {
                        if(var2[var4].name.compareTo(var1[0]) == 0 || var2[var4].alias != null && var2[var4].alias.compareTo(var1[0]) == 0) {
                            var3 = (application.table.column.handler[])var2[var4].table.column;
                            this.canonical_name = (var2[var4].alias != null?var2[var4].alias:var2[var4].name) + ".";
                            break;
                        }
                    }

                    if(var3 != null) {
                        for(var4 = 0; var4 < var3.length; ++var4) {
                            if(var3[var4].name.compareTo(var1[1]) == 0) {
                                this.column = var3[var4];
                                this.canonical_name = this.canonical_name + var1[1];
                                break label85;
                            }
                        }
                    }
                    break;
                default:
                    System.err.println("In Query \'" + this.parent.name + "\' Column format MUST [table].column ");
                    return;
            }

            if(this.column == null) {
                System.err.println("In Query \'" + this.parent.name + "\' Column \'" + this.column_name + "\' Miss");
            }

        }
    }
}
