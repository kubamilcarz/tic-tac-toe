import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameJNITest {
    public GameJNI gameJNI;

    @BeforeEach
    void setup() {
        gameJNI = new GameJNI();
        gameJNI.initializeBoard(); // Reset board before each test
    }

    @Test
    void testBoardInitialization() {
        // Expected board state after initialization:
        // 0 0 0 0
        // 0 0 0 0
        // 0 0 0 0
        // 0 0 0 0
        String boardState = gameJNI.getBoardState();
        String[] rows = boardState.split("\n");
        assertEquals(4, rows.length, "Board should have 4 rows");
        for (String row : rows) {
            assertEquals("0 0 0 0", row.trim(), "Each row should be initialized to empty cells");
        }
    }

    @Test
    void testValidMove() {
        boolean moveMade = gameJNI.makeMove(0, 0); // Attempt move in top-left corner
        assertTrue(moveMade, "Move should be valid in an empty cell");

        // Expected board state after Player X makes a move in the top-left corner:
        // 1 0 0 0
        // 0 0 0 0
        // 0 0 0 0
        // 0 0 0 0
        String boardState = gameJNI.getBoardState();
        String[] rows = boardState.split("\n");
        assertEquals("1 0 0 0", rows[0].trim(), "Top-left cell should be occupied by Player X");

        // Attempt to make an invalid move in the same cell
        moveMade = gameJNI.makeMove(0, 0);
        assertFalse(moveMade, "Move should be invalid if cell is already occupied");
    }

    @Test
    public void testInvalidMoveOutOfBound() {
        boolean moveMade = gameJNI.makeMove(4, 4); // Out of bounds
        assertFalse(moveMade, "Move should be invalid if it's out of bounds");
    }

    @Test
    public void testWinConditionRow() {
        // Set up winning condition manually for Player X in the first row
        gameJNI.makeMove(0, 0); // X
        gameJNI.makeMove(1, 0); // O
        gameJNI.makeMove(0, 1); // X
        gameJNI.makeMove(1, 1); // O
        gameJNI.makeMove(0, 2); // X
        gameJNI.makeMove(1, 2); // O
        gameJNI.makeMove(0, 3); // X (winning move)

        // Expected board state for a winning row:
        // 1 1 1 1
        // 0 0 0 0
        // 0 0 0 0
        // 0 0 0 0
        int winner = gameJNI.checkWinner(1); // 1 represents Player X
        assertTrue(winner == 1, "Player X should win with a row completion");
    }

    @Test
    public void testWinConditionColumn() {
        // Set up winning condition manually for Player O in the first column
        gameJNI.makeMove(0, 0); // X
        gameJNI.makeMove(0, 1); // O
        gameJNI.makeMove(1, 0); // X
        gameJNI.makeMove(1, 1); // O
        gameJNI.makeMove(2, 0); // X
        gameJNI.makeMove(2, 1); // O
        gameJNI.makeMove(3, 1); // O (winning move)

        // Expected board state for a winning column:
        // 0 1 0 0
        int winner = gameJNI.checkWinner(2); // 2 represents Player O
        assertTrue(winner == -1, "Player O should win with a column completion");
    }

    @Test
    public void testWinConditionDiagonal() {
        // Set up winning condition manually for Player X in a diagonal
        gameJNI.makeMove(0, 0); // X
        gameJNI.makeMove(1, 0); // O
        gameJNI.makeMove(1, 1); // X
        gameJNI.makeMove(2, 0); // O
        gameJNI.makeMove(2, 2); // X
        gameJNI.makeMove(3, 0); // O
        gameJNI.makeMove(3, 3); // X (winning move)

        // Expected board state for a winning diagonal:
        // 1 0 0 0
        // 0 1 0 0
        // 0 0 1 0
        // 0 0 0 1
        int winner = gameJNI.checkWinner(1); // 1 represents Player X
        assertTrue(winner == 1, "Player X should win with a diagonal completion");
    }

    @Test
    public void testDrawCondition() {
        int[][] moves = {
                {0, 0}, {0, 1}, {0, 2}, {0, 3},
                {1, 0}, {1, 1}, {1, 2}, {1, 3},
                {2, 0}, {2, 1}, {2, 2}, {2, 3},
                {3, 0}, {3, 1}, {3, 2}, {3, 3}
        };

        int player = 1;
        for (int[] move : moves) {
            gameJNI.makeMove(move[0], move[1]);
            player = (player == 1) ? 2 : 1; // Alternate players
        }

        int game1Result = gameJNI.checkWinner(1);
        int game2Result = gameJNI.checkWinner(2);

        assertEquals(1, game1Result, "Player X should not win in a draw.");
        assertEquals(1, game2Result, "Player O should not win in a draw");
    }
}