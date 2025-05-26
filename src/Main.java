import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Hide Cursor
        System.out.print("\033[?25l");

        // Show Cursor again when Application closes/crashes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.print("\033[?25h");
        }));

        // Setup
        int[] terminalSize = TerminalUtils.getTerminalSize();
        // Leave Space for the Frame
        int rows = terminalSize[0] - 6;
        int cols = terminalSize[1] - 2;

        int generation = 0;

        boolean[][] board = new boolean[rows][cols];
        Random random = new Random();

        // Seed for Random Starting Point
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = random.nextDouble() < 0.2;
            }
        }

        clearScreen();
        printBoard(board, generation);

        while (true) {
            clearScreen();
            board = calculateNewFrame(board);
            printBoard(board, generation++);
            Thread.sleep(500);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printBoard(boolean[][] board, int generation) {
        int cols = board[0].length;

        clearScreen();

        System.out.println("Generation: " + generation);
        System.out.print("+");
        for (int i = 0; i < cols; i++) System.out.print("-");
        System.out.println("+");

        for (boolean[] row : board) {
            System.out.print("|");
            for (boolean cell : row) {
                System.out.print(cell ? "O" : ".");
            }
            System.out.println("|");
        }

        System.out.print("+");
        for (int i = 0; i < cols; i++) System.out.print("-");
        System.out.println("+");
    }

    public static boolean[][] calculateNewFrame(boolean[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] newFrame = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int numberOfNeighbours = calculateNeighbors(row, col, board);
                switch (numberOfNeighbours){
                    case 2:
                        newFrame[row][col] = board[row][col];
                        break;
                    case 3:
                        newFrame[row][col] = true;
                        break;
                    default:
                        newFrame[row][col] = false;
                        break;
                }
            }
        }
        return newFrame;
    }

    public static int calculateNeighbors(int row, int col, boolean[][] board) {
        int count = 0;
        int rows = board.length;
        int cols = board[0].length;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int newRow = row + i;
                int newCol = col + j;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (board[newRow][newCol]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
