//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldhuman.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.Stack;

public class parser extends DefaultHandler {
    private Stack stack = new Stack();
    private StringBuffer sbcp;

    public parser(String var1) {
        this.sbcp = new StringBuffer(var1);
    }

    public void startElement(String var1, String var2, String var3, Attributes var4) {
        try {
            if(this.sbcp.length() > 0) {
                this.sbcp.append(".");
            }

            this.sbcp.append(var3);
            xmlobject var5 = (xmlobject)Class.forName(this.sbcp + ".handler").newInstance();
            if(this.stack.empty()) {
                var5.setparent((xmlobject)null);
            } else {
                xmlobject var6 = (xmlobject)this.stack.peek();
                var5.setparent(var6);
                var6.setchild(var5);
            }

            this.stack.push(var5);
            var5.setattr(var4);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public void endElement(String var1, String var2, String var3) {
        xmlobject var4 = (xmlobject)this.stack.pop();
        if(this.stack.empty()) {
            var4.action();
        } else {
            this.sbcp.delete(this.sbcp.lastIndexOf("."), this.sbcp.length());
        }

    }

    public void characters(char[] var1, int var2, int var3) {
        xmlobject var4 = (xmlobject)this.stack.peek();
        var4.content = var4.content + new String(var1, var2, var3);
    }

    public static void parse(InputStream var0, String var1) {
        try {
            SAXParser var2 = SAXParserFactory.newInstance().newSAXParser();
            var2.parse(var0, new parser(var1));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void parse(InputStream var0) {
        parse(var0, "");
    }
}
