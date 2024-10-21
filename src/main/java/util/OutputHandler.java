package util;

import java.io.PrintStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OutputHandler {
    private static final PrintStream OUT = System.out;

    /**
     * Prints a message with a newline.
     *
     * @param message The message to print.
     */
    public static void println(String message) {
        OUT.println(message);
    }

    /**
     * Prints a message withOUT a newline.
     *
     * @param message The message to print.
     */
    public static void print(String message) {
        OUT.print(message);
    }

    /**
     * Flushes the PrintStream.
     */
    public static void flush() {
        OUT.flush();
    }

    /**
     * Closes the PrintStream.
     */
    public static void close() {
        OUT.close();
    }
}
