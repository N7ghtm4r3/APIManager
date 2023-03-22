package com.tecknobit.apimanager.apis;

import static com.tecknobit.apimanager.apis.ConsolePainter.AnsiModifier.*;

public class ConsolePainter {

    //https://en.wikipedia.org/wiki/ANSI_escape_code

    private static final String ANSI_RESET = "\033[" + AnsiColor.DEFAULT;
    private static final String ANSI_SUFFIX = "#m";

    public enum AnsiColor {

        DEFAULT("0"),
        BLACK("30"),
        RED("31"),
        GREEN("32"),
        YELLOW("33"),
        BLUE("34"),
        MAGENTA("35"),
        CYAN("36"),
        GRAY("37"),
        BRIGHT_GRAY("90"),
        BRIGHT_RED("91"),
        BRIGHT_GREEN("92"),
        BRIGHT_YELLOW("93"),
        BRIGHT_BLUE("94"),
        BRIGHT_MAGENTA("95"),
        BRIGHT_CYAN("96");

        private final String color;

        AnsiColor(String color) {
            this.color = color + "m";
        }

        @Override
        public String toString() {
            return color;
        }

    }

    public enum AnsiModifier {

        BOLD("1"),
        ITALIC("3"),
        UNDERLINED("4"),
        OVERLINED("51");

        private final String modifier;

        AnsiModifier(String modifier) {
            this.modifier = "\033[" + modifier + ";";
        }

        @Override
        public String toString() {
            return modifier;
        }

    }

    public <T> void printBold(T message) {
        printNReset(BOLD + ANSI_SUFFIX + message);
    }

    public <T> void printColouredBold(T message, AnsiColor color) {
        printNReset(BOLD + "" + color + message);
    }

    public <T> void printItalic(T message) {
        printNReset(ITALIC + ANSI_SUFFIX + message);
    }

    public <T> void printColouredItalic(T message, AnsiColor color) {
        printNReset(ITALIC + "" + color + message);
    }

    public <T> void printUnderlined(T message) {
        printNReset(UNDERLINED + ANSI_SUFFIX + message);
    }

    public <T> void printColoredUnderlined(T message, AnsiColor color) {
        printNReset(UNDERLINED + "" + color + message);
    }

    public <T> void printOverlined(T message) {
        printNReset(OVERLINED + ANSI_SUFFIX + message);
    }

    public <T> void printColouredOverlined(T message, AnsiColor color) {
        printNReset(OVERLINED + "" + color + message);
    }

    private void printNReset(String message) {
        System.out.println(message + ANSI_RESET);
    }

}
