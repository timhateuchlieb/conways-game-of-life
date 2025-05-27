import java.util.Random;

public class GameOfLife {
    private final double seedChance;
    private final int frameDelayMs;
    private boolean[][] board;
    private int generation = 0;

    public GameOfLife(double seedChance, int frameDelayMs) {
        this.seedChance = seedChance;
        this.frameDelayMs = frameDelayMs;
        initializeBoard();
    }

    private void initializeBoard() {
        int[] terminalSize = TerminalUtils.getTerminalSize();
        int rows = terminalSize[0] - 4;
        int cols = terminalSize[1] - 2;

        board = new boolean[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = random.nextDouble() < seedChance;
            }
        }
    }

    public void run() throws InterruptedException {
        TerminalUtils.clearScreen();
        printBoard();

        while (true) {
            board = calculateNewFrame();
            generation++;
            printBoard();
            Thread.sleep(frameDelayMs);
        }
    }

    private void printBoard() {
        int cols = board[0].length;

        TerminalUtils.clearScreen();
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

    private boolean[][] calculateNewFrame() {
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] newFrame = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int neighbors = countNeighbors(row, col);
                if (neighbors == 3) {
                    newFrame[row][col] = true;
                } else if (neighbors == 2) {
                    newFrame[row][col] = board[row][col];
                } else {
                    newFrame[row][col] = false;
                }
            }
        }

        return newFrame;
    }

    private int countNeighbors(int row, int col) {
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
