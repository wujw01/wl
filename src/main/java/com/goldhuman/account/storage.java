//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.goldhuman.account;

import application.query.handler;
import com.goldhuman.xml.parser;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class storage {
    private static final DateFormat datefmt = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat datefmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat datefmt3 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    public storage() {
    }

    public static String getNameById(Integer var0) {
        try {
            Object[] var1 = handler.get("getUsername").select("byId").execute(new Object[]{var0}, "auth0");
            if(null != var1 && var1.length > 0) {
                return (String)((Object[])((Object[])var1[0]))[0];
            }
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
        }

        return null;
    }

    public static Object[] acquireIdPasswd(String var0) {
        try {
            Integer var1 = new Integer(0);
            byte[] var2 = null;
            String var3 = "";
            Integer var4 = new Integer(0);
            Object[] var5 = new Object[]{var0.toLowerCase(), var1, var3};
            if(application.procedure.handler.get("acquireuserpasswd").execute(var5, "auth0") == 0) {
                var1 = (Integer)var5[1];
                var3 = (String)var5[2];
                if(null != var3) {
                    var2 = StringPassword(var3);
                }

                if(null != var1 && null != var2 && var1.intValue() != 0) {
                    return new Object[]{var1, var2, var4};
                } else {
                    System.out.println("acquireIdPasswd procedure return null:account=" + var0 + ",uid=" + var1 + ",passwd=" + var2);
                    return null;
                }
            } else {
                System.out.println("acquireIdPasswd procedure failed:account=" + var0);
                return null;
            }
        } catch (Exception var6) {
            var6.printStackTrace(System.out);
            System.out.println("acquireIdPasswd exception:account=" + var0);
            return null;
        }
    }

    public static Object[] acquireIdPasswd2(String var0) {
        try {
            Integer var1 = new Integer(0);
            byte[] var2 = null;
            String var3 = "";
            Object[] var4 = new Object[]{var0.toLowerCase(), var1, var3};
            if(application.procedure.handler.get("acquireuserpasswd2").execute(var4, "auth0") == 0) {
                var1 = (Integer)var4[1];
                var3 = (String)var4[2];
                if(null != var3) {
                    var2 = StringPassword(var3);
                }

                if(null != var1 && null != var2 && var1.intValue() != 0) {
                    return new Object[]{var1, var2};
                } else {
                    System.out.println("acquireIdPasswd2 procedure return null:account=" + var0 + ",uid=" + var1 + ",passwd=" + var2);
                    return null;
                }
            } else {
                System.out.println("acquireIdPasswd2 procedure failed:account=" + var0);
                return null;
            }
        } catch (Exception var5) {
            var5.printStackTrace(System.out);
            System.out.println("acquireIdPasswd2 exception:account=" + var0);
            return null;
        }
    }

    public static Integer getIdByName(String var0) {
        try {
            Integer var1 = new Integer(0);
            Object[] var2 = new Object[]{var0.toLowerCase(), var1};
            if(application.procedure.handler.get("getuseridbyname").execute(var2, "auth0") == 0) {
                var1 = (Integer)var2[1];
                if(null != var1 && 0 != var1.intValue()) {
                    return var1;
                } else {
                    System.out.println("getIdByName uid is fail. name = " + var0);
                    return null;
                }
            } else {
                System.out.println("getIdByName execute false. name = " + var0);
                return null;
            }
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] getUserInfobyName(String var0) {
        try {
            Integer var1 = new Integer(0);
            String var2 = "";
            String var3 = "";
            String var4 = "";
            String var5 = "";
            String var6 = "";
            String var7 = "";
            String var8 = "";
            String var9 = "";
            String var10 = "";
            String var11 = "";
            String var12 = "";
            Integer var13 = new Integer(0);
            String var14 = "";
            String var15 = "";
            String var16 = "";
            Object[] var17 = new Object[]{var0.toLowerCase(), var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16};
            Integer var18 = Integer.valueOf(application.procedure.handler.get("getuserinfobyname").execute(var17, "auth0"));
            if(0 != var18.intValue()) {
                System.out.println("getUserInfoByName return false. name =" + var0 + " ret = " + var18);
                return null;
            } else {
                var1 = (Integer)var17[1];
                var2 = (String)var17[2];
                var3 = (String)var17[3];
                var4 = (String)var17[4];
                var5 = (String)var17[5];
                var6 = (String)var17[6];
                var7 = (String)var17[7];
                var8 = (String)var17[8];
                var9 = (String)var17[9];
                var10 = (String)var17[10];
                var11 = (String)var17[11];
                var12 = (String)var17[12];
                var13 = (Integer)var17[13];
                var14 = (String)var17[14];
                var15 = (String)var17[15];
                var16 = (String)var17[16];
                if(null != var1 && 0 != var1.intValue()) {
                    Date var19 = null;
                    Timestamp var20 = null;
                    if(var14 != null && var14.length() > 0) {
                        var19 = datefmt2.parse(var14);
                    } else {
                        var19 = new Date(0L);
                    }

                    if(var15 != null && var14.length() > 0) {
                        var20 = new Timestamp(datefmt2.parse(var15).getTime());
                    } else {
                        var20 = new Timestamp(0L);
                    }

                    return new Object[]{var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var19, var20, var16};
                } else {
                    System.out.println("getUserInfoByName id is fail. name =" + var0 + " ret = " + var18);
                    return null;
                }
            }
        } catch (Exception var21) {
            var21.printStackTrace(System.out);
            System.out.println("getUserInfoByName execute fail. name =" + var0);
            return null;
        }
    }

    public static boolean addUser(String var0, String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12, Integer var13, String var14, String var15, String var16) {
        try {
            MessageDigest var17 = MessageDigest.getInstance("MD5");
            var17.update(var0.toLowerCase().getBytes());
            var17.update(var1.getBytes());
            MessageDigest var18 = MessageDigest.getInstance("MD5");
            var18.update(var0.toLowerCase().getBytes());
            var18.update(var16.getBytes());
            if(var2 == null) {
                var2 = "";
            }

            if(var3 == null) {
                var3 = "";
            }

            if(var4 == null) {
                var4 = "";
            }

            if(var5 == null) {
                var5 = "";
            }

            if(var6 == null) {
                var6 = "";
            }

            if(var7 == null) {
                var7 = "";
            }

            if(var8 == null) {
                var8 = "";
            }

            if(var9 == null) {
                var9 = "";
            }

            if(var10 == null) {
                var10 = "";
            }

            if(var11 == null) {
                var11 = "";
            }

            if(var12 == null) {
                var12 = "";
            }

            if(var13 == null) {
                var13 = new Integer(2);
            }

            if(var14 == null) {
                var14 = "";
            }

            if(var15 == null) {
                var15 = "";
            }

            Object[] var19 = new Object[]{var0.toLowerCase(), var17.digest(), var2.getBytes("UTF-16LE"), var3.getBytes("UTF-16LE"), var4.getBytes("UTF-16LE"), var5.getBytes("UTF-16LE"), var6.getBytes("UTF-16LE"), var7.getBytes("UTF-16LE"), var8.getBytes("UTF-16LE"), var9.getBytes("UTF-16LE"), var10.getBytes("UTF-16LE"), var11.getBytes("UTF-16LE"), var12.getBytes("UTF-16LE"), var13, var14, var15.getBytes("UTF-16LE"), var18.digest()};
            return application.procedure.handler.get("adduser").execute(var19, "auth0") == 0;
        } catch (Exception var20) {
            var20.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean updateUserInfo(String var0, String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, Integer var12, String var13, String var14) {
        try {
            if(var1 == null) {
                var1 = "";
            }

            if(var2 == null) {
                var2 = "";
            }

            if(var3 == null) {
                var3 = "";
            }

            if(var4 == null) {
                var4 = "";
            }

            if(var5 == null) {
                var5 = "";
            }

            if(var6 == null) {
                var6 = "";
            }

            if(var7 == null) {
                var7 = "";
            }

            if(var8 == null) {
                var8 = "";
            }

            if(var9 == null) {
                var9 = "";
            }

            if(var10 == null) {
                var10 = "";
            }

            if(var11 == null) {
                var11 = "";
            }

            if(var12 == null) {
                var12 = new Integer(2);
            }

            if(var13 == null) {
                var13 = "";
            }

            if(var14 == null) {
                var14 = "";
            }

            Object[] var15 = new Object[]{var0.toLowerCase(), var1.getBytes("UTF-16LE"), var2.getBytes("UTF-16LE"), var3.getBytes("UTF-16LE"), var4.getBytes("UTF-16LE"), var5.getBytes("UTF-16LE"), var6.getBytes("UTF-16LE"), var7.getBytes("UTF-16LE"), var8.getBytes("UTF-16LE"), var9.getBytes("UTF-16LE"), var10.getBytes("UTF-16LE"), var11.getBytes("UTF-16LE"), var12, var13, var14.getBytes("UTF-16LE")};
            return application.procedure.handler.get("updateUserInfo").execute(var15, "auth0") == 0;
        } catch (Exception var16) {
            var16.printStackTrace();
            return false;
        }
    }

    public static boolean tryLogin(String var0, String var1) {
        try {
            Object[] var2 = acquireIdPasswd(var0.toLowerCase());
            if(null == var2) {
                return false;
            } else {
                byte[] var3 = (byte[])((byte[])var2[1]);
                MessageDigest var4 = MessageDigest.getInstance("MD5");
                var4.update(var0.toLowerCase().getBytes());
                var4.update(var1.getBytes());
                byte[] var5 = var4.digest();

                for(int var6 = 0; var6 < var3.length; ++var6) {
                    if(var3[var6] != var5[var6]) {
                        return false;
                    }
                }

                System.out.println("");
                return true;
            }
        } catch (Exception var7) {
            var7.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean tryLogin2(String var0, String var1) {
        try {
            Object[] var2 = acquireIdPasswd2(var0.toLowerCase());
            if(null == var2) {
                return false;
            } else {
                byte[] var3 = (byte[])((byte[])var2[1]);
                MessageDigest var4 = MessageDigest.getInstance("MD5");
                var4.update(var0.toLowerCase().getBytes());
                var4.update(var1.getBytes());
                byte[] var5 = var4.digest();

                for(int var6 = 0; var6 < var3.length; ++var6) {
                    if(var3[var6] != var5[var6]) {
                        return false;
                    }
                }

                return true;
            }
        } catch (Exception var7) {
            var7.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean changePasswdWithOld(String var0, String var1, String var2) {
        try {
            Object[] var3 = acquireIdPasswd(var0.toLowerCase());
            if(null == var3) {
                return false;
            } else {
                byte[] var4 = (byte[])((byte[])var3[1]);
                MessageDigest var5 = MessageDigest.getInstance("MD5");
                var5.update(var0.toLowerCase().getBytes());
                var5.update(var2.getBytes());
                byte[] var6 = var5.digest();

                for(int var7 = 0; var7 < var4.length; ++var7) {
                    if(var4[var7] != var6[var7]) {
                        return false;
                    }
                }

                var5.reset();
                var5.update(var0.toLowerCase().getBytes());
                var5.update(var1.getBytes());
                return application.procedure.handler.get("changePasswd").execute(new Object[]{var0.toLowerCase(), var5.digest()}, "auth0") == 0;
            }
        } catch (Exception var8) {
            var8.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean changePasswdWithOld2(String var0, String var1, String var2) {
        try {
            Object[] var3 = acquireIdPasswd2(var0.toLowerCase());
            if(null == var3) {
                return false;
            } else {
                byte[] var4 = (byte[])((byte[])var3[1]);
                MessageDigest var5 = MessageDigest.getInstance("MD5");
                var5.update(var0.toLowerCase().getBytes());
                var5.update(var2.getBytes());
                byte[] var6 = var5.digest();

                for(int var7 = 0; var7 < var4.length; ++var7) {
                    if(var4[var7] != var6[var7]) {
                        return false;
                    }
                }

                var5.reset();
                var5.update(var0.toLowerCase().getBytes());
                var5.update(var1.getBytes());
                return application.procedure.handler.get("changePasswd").execute(new Object[]{var0.toLowerCase(), var5.digest()}, "auth0") == 0;
            }
        } catch (Exception var8) {
            var8.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean changePasswd2WithOld2(String var0, String var1, String var2) {
        try {
            Object[] var3 = acquireIdPasswd2(var0.toLowerCase());
            if(null == var3) {
                return false;
            } else {
                byte[] var4 = (byte[])((byte[])var3[1]);
                MessageDigest var5 = MessageDigest.getInstance("MD5");
                var5.update(var0.toLowerCase().getBytes());
                var5.update(var2.getBytes());
                byte[] var6 = var5.digest();

                for(int var7 = 0; var7 < var4.length; ++var7) {
                    if(var4[var7] != var6[var7]) {
                        return false;
                    }
                }

                var5.reset();
                var5.update(var0.toLowerCase().getBytes());
                var5.update(var1.getBytes());
                return application.procedure.handler.get("changePasswd2").execute(new Object[]{var0.toLowerCase(), var5.digest()}, "auth0") == 0;
            }
        } catch (Exception var8) {
            var8.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean changePasswd(String var0, String var1) {
        try {
            MessageDigest var2 = MessageDigest.getInstance("MD5");
            var2.update(var0.toLowerCase().getBytes());
            var2.update(var1.getBytes());
            return application.procedure.handler.get("changePasswd").execute(new Object[]{var0.toLowerCase(), var2.digest()}, "auth0") == 0;
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean changePasswd2(String var0, String var1) {
        try {
            MessageDigest var2 = MessageDigest.getInstance("MD5");
            var2.update(var0.toLowerCase().getBytes());
            var2.update(var1.getBytes());
            return application.procedure.handler.get("changePasswd2").execute(new Object[]{var0.toLowerCase(), var2.digest()}, "auth0") == 0;
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
            return false;
        }
    }

    public static byte[] StringPassword(String var0) {
        byte[] var1 = new byte[16];

        for(int var2 = 1; var2 <= 16; ++var2) {
            var1[var2 - 1] = (byte)Integer.parseInt(var0.substring(var2 * 2, var2 * 2 + 2), 16);
        }

        return var1;
    }

    public static Object[] getIPLimit(Integer var0) {
        try {
            Object[] var1 = (Object[])handler.get("getIPLimit").select("byUid").execute(new Object[]{var0}, "auth0");
            return null != var1 && var1.length > 0?(Object[])((Object[])var1[0]):null;
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    public static int checkIPLimit(Integer var0, Integer var1) {
        try {
            Object[] var2 = getIPLimit(var0);
            if(null == var2) {
                return 0;
            } else if(null != var2[8] && ((String)var2[8]).equals("t")) {
                return 1;
            } else if(null != var2[7] && ((String)var2[7]).equals("t")) {
                boolean var3 = false;

                for(int var4 = 1; var4 <= 5; var4 += 2) {
                    if(null != var2[var4] && null != var2[var4 + 1]) {
                        int var5 = ((Integer)var2[var4]).intValue();
                        byte var6 = Byte.valueOf((String)var2[var4 + 1]).byteValue();
                        if(var6 > 0 && var6 <= 32) {
                            var3 = true;
                            int var7 = 32 - var6;
                            System.out.println("checkIPLimit: uid=" + var0 + ",ip=" + var1 + ",ipaddr=" + var5 + ",ipmask=" + var6);
                            if((var1.intValue() & -1 << var7) == (var5 & -1 << var7)) {
                                return 0;
                            }
                        }
                    }
                }

                return var3?2:0;
            } else {
                return 0;
            }
        } catch (Exception var8) {
            var8.printStackTrace(System.out);
            return 0;
        }
    }

    public static boolean setIPLimit(Integer var0, Integer var1, Byte var2, Integer var3, Byte var4, Integer var5, Byte var6) {
        try {
            Object[] var7 = new Object[]{var0, var1, var2.toString(), var3, var4.toString(), var5, var6.toString()};
            return application.procedure.handler.get("setiplimit").execute(var7, "auth0") == 0;
        } catch (Exception var8) {
            var8.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean enableIPLimit(Integer var0, boolean var1) {
        try {
            String var2 = var1?new String("t"):new String("f");
            Object[] var3 = new Object[]{var0, var2};
            return application.procedure.handler.get("enableiplimit").execute(var3, "auth0") == 0;
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean getLockStatus(Integer var0) {
        try {
            Object[] var1 = (Object[])getIPLimit(var0);
            return null != var1 && null != var1[8] && ((String)var1[8]).equals("t");
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean lockUser(Integer var0, boolean var1) {
        try {
            String var2 = var1?new String("t"):new String("f");
            Object[] var3 = new Object[]{var0, var2};
            return application.procedure.handler.get("lockuser").execute(var3, "auth0") == 0;
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
            return false;
        }
    }

    public static Object[] acquirePrivilegeByUidZid(Integer var0, Integer var1) {
        try {
            return (Object[])handler.get("acquirePrivilege").select("byUidZid").execute(new Object[]{var0, var1}, "auth0");
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] acquirePrivilegeByUid(Integer var0) {
        try {
            return (Object[])handler.get("acquirePrivilege").select("byUid").execute(new Object[]{var0}, "auth0");
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] acquirePrivilegeByZid(Integer var0) {
        try {
            return (Object[])handler.get("acquirePrivilege").select("byZid").execute(new Object[]{var0}, "auth0");
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] acquirePrivilege() {
        try {
            return (Object[])handler.get("acquirePrivilege").select("byAll").execute(new Object[0], "auth0");
        } catch (Exception var1) {
            var1.printStackTrace(System.out);
            return null;
        }
    }

    public static int acquireUserCreatime(Integer var0) {
        try {
            Object[] var1 = (Object[])handler.get("acquireUserCreatime").select("byUid").execute(new Object[]{var0}, "auth0");
            return null != var1 && var1.length > 0?(int)(((Date)((Object[])((Object[])var1[0]))[0]).getTime() / 1000L):0;
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return 0;
        }
    }

    public static void addUserPriv(Integer var0, Integer var1, Integer var2) {
        try {
            Object[] var3 = new Object[]{var0, var1, var2};
            application.procedure.handler.get("addUserPriv").execute(var3, "auth0");
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
        }

    }

    public static void delUserPriv(Integer var0, Integer var1, Integer var2) {
        try {
            Object[] var3 = new Object[]{var0, var1, var2, new Integer(0)};
            application.procedure.handler.get("delUserPriv").execute(var3, "auth0");
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
        }

    }

    public static void replaceUserPriv(Integer var0, Integer var1, Set var2) {
        try {
            Object[] var3 = new Object[]{var0, var1, new Integer(0), new Integer(1)};
            application.procedure.handler.get("delUserPriv").execute(var3, "auth0");
            var3 = new Object[]{var0, var1, null};
            Iterator var4 = var2.iterator();

            while(var4.hasNext()) {
                var3[2] = (Integer)var4.next();
                application.procedure.handler.get("addUserPriv").execute(var3, "auth0");
            }
        } catch (Exception var5) {
            var5.printStackTrace(System.out);
        }

    }

    public static void delUserPriv(Integer var0, Integer var1) {
        try {
            Object[] var2 = new Object[]{var0, var1, new Integer(0), new Integer(1)};
            application.procedure.handler.get("delUserPriv").execute(var2, "auth0");
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
        }

    }

    public static void delUserPriv(Integer var0) {
        try {
            Object[] var1 = new Object[]{var0, new Integer(0), new Integer(0), new Integer(2)};
            application.procedure.handler.get("delUserPriv").execute(var1, "auth0");
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
        }

    }

    public static Object[] acquireUserPrivilege(Integer var0, Integer var1) {
        try {
            return (Object[])handler.get("acquireUserPrivilege").select("byUidZid").execute(new Object[]{var0, var1}, "auth0");
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] getUserForbidByName(String var0) {
        try {
            return (Object[])handler.get("acquireForbid").select("byName").execute(new Object[]{var0.toLowerCase()}, "auth0");
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] acquireForbid(Integer var0) {
        try {
            return (Object[])handler.get("acquireForbid").select("byUid").execute(new Object[]{var0}, "auth0");
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    public static boolean deleteTimeoutForbid(Integer var0) {
        try {
            Object[] var1 = new Object[]{var0};
            return application.procedure.handler.get("deleteTimeoutForbid").execute(var1, "auth0") == 0;
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean deleteForbid(Integer var0, Integer var1) {
        try {
            Object[] var2 = new Object[]{var0, var1};
            return application.procedure.handler.get("deleteForbid").execute(var2, "auth0") == 0;
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
            return false;
        }
    }

    public static boolean addForbid(Integer var0, Integer var1, Integer var2, byte[] var3, Integer var4) {
        try {
            Object[] var5 = new Object[]{var0, var1, var2, var3, var4};
            return application.procedure.handler.get("addForbid").execute(var5, "auth0") == 0;
        } catch (Exception var6) {
            var6.printStackTrace(System.out);
            return false;
        }
    }

    public static Object[] getUserOnlineInfo(Integer var0) {
        try {
            return (Object[])handler.get("getUserOnlineInfo").select("byUid").execute(new Object[]{var0}, "auth0");
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    public static void clearOnlineRecords(Integer var0, Integer var1) {
        try {
            Object[] var2 = new Object[]{var0, var1};
            application.procedure.handler.get("clearonlinerecords").execute(var2, "auth0");
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
        }

    }

    public static boolean recordUserOnline(Object[] var0, Integer var1, Integer var2) {
        try {
            Object[] var3 = new Object[]{var1, var2, var0[0], var0[1], var0[2]};
            if(application.procedure.handler.get("recordonline").execute(var3, "auth0") == 0) {
                var0[0] = var3[2];
                var0[1] = var3[3];
                var0[2] = var3[4];
                return true;
            }
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
        }

        return false;
    }

    public static boolean recordUserOffline(Object[] var0, Integer var1, Integer var2) {
        try {
            Object[] var3 = new Object[]{var1, var2, var0[0], var0[1], var0[2]};
            if(application.procedure.handler.get("recordoffline").execute(var3, "auth0") == 0) {
                var0[2] = var3[4];
                return true;
            }
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
        }

        return false;
    }

    public static Object[] getUserPoints(Integer var0) {
        try {
            return handler.get("getUserPoints").select("byuid").execute(new Object[]{var0}, "auth0");
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] acquireRemainTime(Integer var0, Integer var1) {
        try {
            Object[] var2 = new Object[]{var0, var1, new Integer(0), new Integer(0)};
            application.procedure.handler.get("remaintime").execute(var2, "auth0");
            Integer var3 = new Integer(0);
            if(null != var2[3] && ((Integer)var2[3]).intValue() > 0) {
                var3 = (Integer)var2[3];
            }

            return new Object[]{(Integer)var2[2], var3};
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
            return null;
        }
    }

    public static boolean addUserPoint(Integer var0, Integer var1, Integer var2) {
        try {
            Object[] var3 = new Object[]{var0, var1, var2};
            return application.procedure.handler.get("adduserpoint").execute(var3, "auth0") == 0;
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
            return false;
        }
    }

    public static int useCash(Integer var0, Integer var1, Integer var2, Integer var3, Integer var4, Integer var5, Integer var6) {
        try {
            Integer var7 = new Integer(0);
            Object[] var8 = new Object[]{var0, var1, var2, var3, var4, var5, var6, var7};
            application.procedure.handler.get("usecash").execute(var8, "auth0");
            return ((Integer)var8[7]).intValue();
        } catch (Exception var9) {
            var9.printStackTrace(System.out);
            return -1;
        }
    }

    public static Object[] getUseCashNow(Integer var0) {
        try {
            return handler.get("getusecashnow").select("bystatus").execute(new Object[]{var0});
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] getUseCashNow(Integer var0, Integer var1) {
        try {
            return handler.get("getusecashnow").select("byuserzone").execute(new Object[]{var0, var1});
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] getUseCashNow(Integer var0, Integer var1, Integer var2) {
        try {
            return handler.get("getusecashnow").select("byuserzonesn").execute(new Object[]{var0, var1, var2});
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] getUseCashNowByUser(Integer var0) {
        try {
            return handler.get("getusecashnow").select("byuser").execute(new Object[]{var0});
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] getUseCashLog(Integer var0, Integer var1) {
        try {
            return handler.get("getusecashlog").select("byuserzone").execute(new Object[]{var0, var1});
        } catch (Exception var3) {
            var3.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] getUseCashLog(Integer var0, Integer var1, Integer var2) {
        try {
            return handler.get("getusecashlog").select("byuserzonesn").execute(new Object[]{var0, var1, var2});
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
            return null;
        }
    }

    public static Object[] getUseCashLogByUser(Integer var0) {
        try {
            return handler.get("getusecashlog").select("byuser").execute(new Object[]{var0});
        } catch (Exception var2) {
            var2.printStackTrace(System.out);
            return null;
        }
    }

    private static String toHexString(byte[] var0) {
        StringBuffer var1 = new StringBuffer(var0.length * 2);

        for(int var2 = 0; var2 < var0.length; ++var2) {
            byte var3 = var0[var2];
            int var4 = var3 >> 4 & 15;
            var1.append((char)(var4 >= 10?97 + var4 - 10:48 + var4));
            var4 = var3 & 15;
            var1.append((char)(var4 >= 10?97 + var4 - 10:48 + var4));
        }

        return var1.toString();
    }

    public static void main(String[] var0) {
        try {
            parser.parse(new FileInputStream(var0[0]));
            System.out.println("Done!");
            Object[] var1 = acquireIdPasswd("zengpan");
            Integer var2 = (Integer)var1[0];
            byte[] var3 = (byte[])((byte[])var1[1]);
            System.out.println("uid=" + var2 + ",passwd=" + var3);

            for(int var4 = 0; var4 < var3.length; ++var4) {
                System.out.print(Byte.toString(var3[var4]));
            }

            System.out.println("");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}
