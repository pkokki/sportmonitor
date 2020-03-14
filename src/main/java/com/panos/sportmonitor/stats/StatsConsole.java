package com.panos.sportmonitor.stats;

import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

public class StatsConsole {
    public static void printlnInfo(String msg) {
        println(AnsiColor.BLUE, msg);
    }
    public static  void printlnError(String msg) {
        println(AnsiColor.RED, msg);
    }
    public static  void printlnWarn(String msg) {
        println(AnsiColor.YELLOW, msg);
    }
    public static  void printlnState(String msg) {
        println(AnsiColor.GREEN, msg);
    }
    private static void println(AnsiColor color, String msg) {
        System.out.println(AnsiOutput.toString("\u001B[", color.toString(), "m", msg, "\u001B[0m"));
    }
}
