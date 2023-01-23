//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldhuman.xml;

import org.xml.sax.Attributes;

public abstract class xmlobject {
    public String name;
    public String content = "";
    public xmlobject parent;
    public xmlobject[] children = new xmlobject[0];

    public xmlobject() {
    }

    protected void setchild(xmlobject var1) {
        xmlobject[] var2 = new xmlobject[this.children.length + 1];
        System.arraycopy(this.children, 0, var2, 0, this.children.length);
        var2[this.children.length] = var1;
        this.children = var2;
    }

    protected void setparent(xmlobject var1) {
        this.parent = var1;
    }

    protected void setattr(Attributes var1) {
        this.name = var1.getValue("name");
    }

    public abstract void action();
}
