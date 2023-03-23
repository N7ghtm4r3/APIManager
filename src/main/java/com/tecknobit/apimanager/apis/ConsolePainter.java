package com.tecknobit.apimanager.apis;

import static com.tecknobit.apimanager.apis.ConsolePainter.ANSIModifier.*;

/**
 * The {@code ConsolePainter} class is useful to print out any text in the console in different styles with {@code "ANSI"}
 * escape characters
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/ConsolePainter.md">ConsolePainter.md</a>
 * @since 2.1.1
 **/
public class ConsolePainter {

    /**
     * {@code ANSIColor} list of available ANSI colors
     **/
    public enum ANSIColor {

        /**
         * {@code DEFAULT} ANSI color
         **/
        DEFAULT("0"),

        /**
         * {@code BLACK} ANSI color
         **/
        BLACK("30"),

        /**
         * {@code ANSIColor} ANSI color
         **/
        RED("31"),

        /**
         * {@code GREEN} ANSI color
         **/
        GREEN("32"),

        /**
         * {@code YELLOW} ANSI color
         **/
        YELLOW("33"),

        /**
         * {@code BLUE} ANSI color
         **/
        BLUE("34"),

        /**
         * {@code MAGENTA} ANSI color
         **/
        MAGENTA("35"),

        /**
         * {@code CYAN} ANSI color
         **/
        CYAN("36"),

        /**
         * {@code GRAY} ANSI color
         **/
        GRAY("37"),

        /**
         * {@code BRIGHT_GRAY} ANSI color
         **/
        BRIGHT_GRAY("90"),

        /**
         * {@code BRIGHT_RED} ANSI color
         **/
        BRIGHT_RED("91"),

        /**
         * {@code BRIGHT_GREEN} ANSI color
         **/
        BRIGHT_GREEN("92"),

        /**
         * {@code BRIGHT_YELLOW} ANSI color
         **/
        BRIGHT_YELLOW("93"),

        /**
         * {@code BRIGHT_BLUE} ANSI color
         **/
        BRIGHT_BLUE("94"),

        /**
         * {@code BRIGHT_MAGENTA} ANSI color
         **/
        BRIGHT_MAGENTA("95"),

        /**
         * {@code BRIGHT_CYAN} ANSI color
         **/
        BRIGHT_CYAN("96");

        /**
         * {@code color} ANSI color value
         **/
        private final String color;

        /**
         * Constructor to init {@link ANSIColor}
         *
         * @param color: ANSI color value
         **/
        ANSIColor(String color) {
            this.color = color + "m";
        }

        /**
         * Method to get {@link #color} instance <br>
         * Any params required
         *
         * @return {@link #color} instance as {@link String}
         **/
        @Override
        public String toString() {
            return color;
        }

    }

    /**
     * {@code ANSIModifier} list of available ANSI modifiers
     **/
    public enum ANSIModifier {

        /**
         * {@code BOLD} ANSI modifier
         **/
        BOLD("1"),

        /**
         * {@code ITALIC} ANSI modifier
         **/
        ITALIC("3"),

        /**
         * {@code UNDERLINED} ANSI modifier
         **/
        UNDERLINED("4"),

        /**
         * {@code INVERT} ANSI modifier
         **/
        INVERT("7"),

        /**
         * {@code CROSSED_OUT} ANSI modifier
         **/
        CROSSED_OUT("9"),

        /**
         * {@code DOUBLY_UNDERLINED} ANSI modifier
         **/
        DOUBLY_UNDERLINED("21"),

        /**
         * {@code FRAMED} ANSI modifier
         **/
        FRAMED("51");

        /**
         * {@code modifier} ANSI modifier value
         **/
        private final String modifier;

        /**
         * Constructor to init {@link ANSIModifier}
         *
         * @param modifier: ANSI modifier value
         **/
        ANSIModifier(String modifier) {
            this.modifier = "\033[" + modifier + ";";
        }

        /**
         * Method to get {@link #modifier} instance <br>
         * Any params required
         *
         * @return {@link #modifier} instance as {@link String}
         **/
        @Override
        public String toString() {
            return modifier;
        }

    }

    /**
     * {@code ANSI_SUFFIX} ANSI suffix constant
     **/
    private static final String ANSI_SUFFIX = "#m";

    /**
     * Method to print a colored text in the console
     *
     * @param message: message to print
     * @param color:   color value for the message
     **/
    public <T> void printColored(T message, ANSIColor color) {
        printNReset("\033[" + color + message);
    }

    /**
     * Method to print a bold text in the console
     *
     * @param message: message to print
     **/
    public <T> void printBold(T message) {
        printNReset(BOLD + ANSI_SUFFIX + message);
    }

    /**
     * Method to print a colored bold text in the console
     *
     * @param message: message to print
     * @param color:   color value for the message
     **/
    public <T> void printBold(T message, ANSIColor color) {
        printNReset(BOLD + "" + color + message);
    }

    /**
     * Method to print an italic text in the console
     *
     * @param message: message to print
     **/
    public <T> void printItalic(T message) {
        printNReset(ITALIC + ANSI_SUFFIX + message);
    }

    /**
     * Method to print an italic colored text in the console
     *
     * @param message: message to print
     * @param color:   color value for the message
     **/
    public <T> void printItalic(T message, ANSIColor color) {
        printNReset(ITALIC + "" + color + message);
    }

    /**
     * Method to print an underlined text in the console
     *
     * @param message: message to print
     **/
    public <T> void printUnderlined(T message) {
        printNReset(UNDERLINED + ANSI_SUFFIX + message);
    }

    /**
     * Method to print an underlined colored text in the console
     *
     * @param message: message to print
     * @param color:   color value for the message
     **/
    public <T> void printUnderlined(T message, ANSIColor color) {
        printNReset(UNDERLINED + "" + color + message);
    }

    /**
     * Method to print an invert text in the console
     *
     * @param message: message to print
     **/
    public <T> void printInvert(T message) {
        printNReset(INVERT + ANSI_SUFFIX + message);
    }

    /**
     * Method to print an invert colored text in the console
     *
     * @param message: message to print
     * @param color:   color value for the message
     **/
    public <T> void printInvert(T message, ANSIColor color) {
        printNReset(INVERT + "" + color + message);
    }

    /**
     * Method to print a crossed-out text in the console
     *
     * @param message: message to print
     **/
    public <T> void printCrossedOut(T message) {
        printNReset(CROSSED_OUT + ANSI_SUFFIX + message);
    }

    /**
     * Method to print a crossed-out colored text in the console
     *
     * @param message: message to print
     * @param color:   color value for the message
     **/
    public <T> void printCrossedOut(T message, ANSIColor color) {
        printNReset(CROSSED_OUT + "" + color + message);
    }

    /**
     * Method to print a doubly underlined text in the console
     *
     * @param message: message to print
     **/
    public <T> void printDoublyUnderlined(T message) {
        printNReset(DOUBLY_UNDERLINED + ANSI_SUFFIX + message);
    }

    /**
     * Method to print a doubly underlined colored text in the console
     *
     * @param message: message to print
     * @param color:   color value for the message
     **/
    public <T> void printDoublyUnderlined(T message, ANSIColor color) {
        printNReset(DOUBLY_UNDERLINED + "" + color + message);
    }

    /**
     * Method to print a framed text in the console
     *
     * @param message: message to print
     **/
    public <T> void printFramed(T message) {
        printNReset(FRAMED + ANSI_SUFFIX + message);
    }

    /**
     * Method to print a framed colored text in the console
     *
     * @param message: message to print
     * @param color:   color value for the message
     **/
    public <T> void printFramed(T message, ANSIColor color) {
        printNReset(FRAMED + "" + color + message);
    }

    /**
     * Method to print a text in the console and reset the console to default status
     *
     * @param message: message to print
     **/
    private void printNReset(String message) {
        System.out.println(message + "\033[" + ANSIColor.DEFAULT);
    }

}
