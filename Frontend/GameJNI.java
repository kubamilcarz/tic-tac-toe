public class GameJNI {
    static {
        System.loadLibrary("backend");
    }

    public native void initializeBoard();
    public native boolean makeMove(int x, int y);
    public native String getBoardState();
    public native int checkWinner(int player);
}