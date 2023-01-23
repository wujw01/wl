//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package application.query.table;

import com.goldhuman.xml.xmlobject;
import org.xml.sax.Attributes;

public class handler extends xmlobject {
    public String alias;
    public application.table.handler table;

    public handler() {
    }

    protected void setattr(Attributes var1) {
        super.setattr(var1);
        this.alias = var1.getValue("alias");
    }

    public void action() {
        xmlobject[] var1 = this.parent.parent.children;

        for(int var2 = 0; var2 < var1.length; ++var2) {
            if(var1[var2] instanceof application.table.handler && var1[var2].name.compareTo(this.name) == 0) {
                this.table = (application.table.handler)var1[var2];
                break;
            }
        }

        if(this.table == null) {
            System.err.println("In Query \'" + this.parent.name + "\' table \'" + this.name + "\' Miss");
        }

    }
}
