#include "library.h"
#include <vector>
#include <string>
#include <sstream>

std::vector<std::vector<int>> board;
int currentPlayer = 1; // 1 for player X, 2 for player O

JNIEXPORT void JNICALL Java_GameJNI_initializeBoard(JNIEnv *, jobject) {
    board = std::vector<std::vector<int>>(4, std::vector<int>(4, 0));
    currentPlayer = 1;
}

bool isValidMove(int x, int y) {
    // Check if coordinates are within bounds and the cell is empty before the move happens
    return x >= 0 && x < 4 && y >= 0 && y < 4 && board[y][x] == 0;
}

JNIEXPORT jboolean JNICALL Java_GameJNI_makeMove(JNIEnv *, jobject, jint x, jint y) {
    if (isValidMove(x, y)) {
        board[y][x] = currentPlayer;

        // Switch players
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        return JNI_TRUE;
    }

    return JNI_FALSE; // Invalid move
}

std::string getBoardAsString() {
    std::ostringstream oss;
    for (const auto& row : board) {
        for (int cell : row) {
            oss << cell << " ";
        }
        oss << "\n";
    }
    return oss.str();
}

JNIEXPORT jstring JNICALL Java_GameJNI_getBoardState(JNIEnv *env, jobject) {
    std::string state = getBoardAsString();
    return env->NewStringUTF(state.c_str());
}

int checkWinner(int player) {
    // Check rows and columns for a win
    for (int i = 0; i < 4; ++i) {
        if ((board[i][0] == 1 && board[i][1] == 1 && board[i][2] == 1 && board[i][3] == 1) ||
            (board[0][i] == 1 && board[1][i] == 1 && board[2][i] == 1 && board[3][i] == 1)) {
            return 1; // Player X wins
            }
        if ((board[i][0] == 2 && board[i][1] == 2 && board[i][2] == 2 && board[i][3] == 2) ||
            (board[0][i] == 2 && board[1][i] == 2 && board[2][i] == 2 && board[3][i] == 2)) {
            return 2; // Player O wins
            }
    }

    // Check diagonals for a win
    if ((board[0][0] == 1 && board[1][1] == 1 && board[2][2] == 1 && board[3][3] == 1) ||
        (board[0][3] == 1 && board[1][2] == 1 && board[2][1] == 1 && board[3][0] == 1)) {
        return 1; // Player X wins
        }
    if ((board[0][0] == 2 && board[1][1] == 2 && board[2][2] == 2 && board[3][3] == 2) ||
        (board[0][3] == 2 && board[1][2] == 2 && board[2][1] == 2 && board[3][0] == 2)) {
        return 2; // Player O wins
        }

    // Check for a tie by counting all filled cells
    int filledCells = 0;
    for (const auto& row : board) {
        for (int cell : row) {
            if (cell != 0) {
                filledCells++;
            }
        }
    }

    // If all cells are filled and there's no winner, it's a tie
    if (filledCells == 16) {
        return 0; // Tie
    }

    // No winner yet and the board is not full
    // Game continues
    return -1;
}

// JNI function to check if a player has won
JNIEXPORT jint JNICALL Java_GameJNI_checkWinner(JNIEnv *, jobject, jint player) {
    return checkWinner(player);
}