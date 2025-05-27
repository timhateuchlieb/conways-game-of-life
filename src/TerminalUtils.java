import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TerminalUtils {

    public static void hideCursor() {
        System.out.print("\033[?25l");
    }

    public static void showCursor() {
        System.out.print("\033[?25h");
    }
    public static void registerCursorShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(TerminalUtils::showCursor));
    }

    public static int[] getTerminalSize() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty size < /dev/tty"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();

            if (line != null) {
                String[] parts = line.trim().split("\\s+");
                int rows = Integer.parseInt(parts[0]);
                int cols = Integer.parseInt(parts[1]);
                return new int[]{rows, cols};
            }
        } catch (Exception e) {
            System.err.println("Could not detect terminal size. Using default.");
        }

        return new int[]{20, 40};
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }



}
