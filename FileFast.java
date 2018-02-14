import java.io.IOException;
import java.io.PrintWriter;

public class FileFast {
    public static void main(String[] args) {
        System.out.println("Hi there!");

        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Please provide number of lines");
            }

            final int lines = Integer.parseInt(args[0]);
            if (lines <= 0) {
                throw new IllegalArgumentException("N shall be positive");
            }

            writeLinesFast(lines);

        } catch (Exception e) {
            usage(e);
        }
    }

    private static void usage(Exception e) {
        System.out.println("Error: " + e.toString());
        System.out.println("MSG: " + e.getMessage());
        System.out.println("Usage: FileSlow <N>");
        System.out.println("Where N - number of lines to write.");
    }

    private static void writeLinesFast(final int lines) throws IOException {
        System.out.printf("Writing %d lines...\n", lines);

        try (PrintWriter fw = new PrintWriter("lines.txt")) {
            for (int i = 0; i < lines; i++) {
                fw.printf("Line: %d\n", i);
            }
        }
    }
}
