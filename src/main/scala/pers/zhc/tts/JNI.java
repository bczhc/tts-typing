package pers.zhc.tts;

public class JNI {
    public static native long create();

    public static native void speak(long addr, String text);

    public static native void release(long addr);

    public static native void setPriority(long addr, int priority);

    public static native void setRate(long addr, int rate);
}
