package cn.johnnyzen.common.util.i18n;

public interface LocalMessage {
    String get(String var1);

    String getOrDef(String var1, String var2);

    String get(String var1, Object[] var2, String var3);

    String get(String var1, Object... var2);

    String getOrDef(String var1, String var2, Object... var3);
}
