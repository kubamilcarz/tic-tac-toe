import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private GameJNI gameJNI;
    private int currentPlayer;

    public TicTacToeGUI() {
        gameJNI = new GameJNI();
        gameJNI.initializeBoard();
        currentPlayer = 1; // Start with Player X

        setTitle("4x4 Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 4));

        buttons = new JButton[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.PLAIN, 40));
                final int r = row;
                final int c = col;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(r, c);
                    }
                });

                buttons[row][col] = button;
                add(button);
            }
        }

        setVisible(true);
    }

    private void handleButtonClick(int row, int col) {
        if (gameJNI.makeMove(col, row)) {
            updateBoard();

            if (gameJNI.checkWinner(currentPlayer) == 1) {
                JOptionPane.showMessageDialog(this, "Player " + (currentPlayer == 1 ? "X" : "O") + " wins!");
                resetBoard();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                resetBoard();
            } else {
                // Switch player if no win or draw
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move. Try again.");
        }
    }

    private boolean isBoardFull() {
        int moveCount = 0;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (!buttons[row][col].getText().equals("")) {
                    moveCount++;
                }
            }
        }
        return moveCount == 16;
    }

    private void updateBoard() {
        String boardState = gameJNI.getBoardState();
        String[] rows = boardState.split("\n");
        for (int i = 0; i < 4; i++) {
            String[] cells = rows[i].split(" ");
            for (int j = 0; j < 4; j++) {
                String cell = cells[j];
                buttons[i][j].setText(cell.equals("0") ? "" : (cell.equals("1") ? "X" : "O"));
            }
        }
    }

    private void resetBoard() {
        gameJNI.initializeBoard();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                buttons[row][col].setText("");
            }
        }

        currentPlayer = 1;
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}