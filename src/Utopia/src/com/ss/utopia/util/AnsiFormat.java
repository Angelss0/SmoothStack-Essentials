package com.ss.utopia.util;

public class AnsiFormat {
    public static enum Colors {
        Black, Red, Green, Yellow, Blue, Magenta, Cyan, White,
        BrightBlack, BrightRed, BrightGreen, BrightYellow, BrightBlue, BrightMagenta, BrightCyan, BrightWhite
    }

    public static final String setToBold(String string) { return "\033[1m" + string + "\033[0m"; }
    public static final String setToItalic(String string) { return "\033[3m" + string + "\033[0m"; }
    public static final String setColor(String string, Colors colorCode) { return "\033[38;5;" + colorCode.ordinal() + "m" + string + "\033[0m"; }
    public static final String setBackgroundColor(String string, Colors colorCode) { return "\033[48;5;" + colorCode.ordinal() + "m" + string + "\033[0m"; }
}
