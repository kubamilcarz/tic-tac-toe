#ifndef LIBRARY_H
#define LIBRARY_H

#include <jni.h>
#include <vector>
#include <string>

extern std::vector<std::vector<int>> board; // 4x4 game board
extern int currentPlayer; // Tracks the current player (1 for X, 2 for O)

#ifdef __cplusplus
extern "C" {
#endif

    JNIEXPORT void JNICALL Java_GameJNI_initializeBoard(JNIEnv *, jobject);

    JNIEXPORT jboolean JNICALL Java_GameJNI_makeMove(JNIEnv *, jobject, jint x, jint y);

    JNIEXPORT jstring JNICALL Java_GameJNI_getBoardState(JNIEnv *env, jobject);

    JNIEXPORT jint JNICALL Java_GameJNI_checkWinner(JNIEnv *, jobject, jint player);

#ifdef __cplusplus
}
#endif

#endif // LIBRARY_H