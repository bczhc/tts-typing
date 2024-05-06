#ifndef JNI_LIB_HH
#define JNI_LIB_HH

#include <jni.h>

extern "C" {
JNIEXPORT jlong JNICALL Java_pers_zhc_tts_JNI_create(JNIEnv *env, jclass _);

JNIEXPORT void JNICALL Java_pers_zhc_tts_JNI_speak(JNIEnv *env, jclass _, jlong addr, jstring j_text);

JNIEXPORT void JNICALL Java_pers_zhc_tts_JNI_release(JNIEnv *env, jclass _, jlong addr);

JNIEXPORT void JNICALL Java_pers_zhc_tts_JNI_setPriority(JNIEnv *env, jclass _, jlong addr, jint priority);

JNIEXPORT void JNICALL Java_pers_zhc_tts_JNI_setRate(JNIEnv *env, jclass _, jlong addr, jint rate);
}

#endif //JNI_LIB_HH
