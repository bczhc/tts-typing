#include "jni_lib.hh"
#include <jni.h>
#include "sapi-tts/tts.hh"

JNIEXPORT jlong JNICALL Java_pers_zhc_tts_JNI_create(JNIEnv *env, jclass _) {
    return reinterpret_cast<jlong>(new TTS);
}

JNIEXPORT void JNICALL Java_pers_zhc_tts_JNI_speak(JNIEnv *env, jclass _, const jlong addr, jstring j_text) {
    const auto text = env->GetStringUTFChars(j_text, nullptr);
    reinterpret_cast<TTS *>(addr)->speak(text);
}

JNIEXPORT void JNICALL Java_pers_zhc_tts_JNI_release(JNIEnv *env, jclass _, const jlong addr) {
    delete reinterpret_cast<TTS *>(addr);
}

JNIEXPORT void JNICALL Java_pers_zhc_tts_JNI_setPriority(JNIEnv *env, jclass _, const jlong addr, jint priority) {
    reinterpret_cast<TTS *>(addr)->set_priority(static_cast<SPVPRIORITY>(priority));
}

JNIEXPORT void JNICALL Java_pers_zhc_tts_JNI_setRate(JNIEnv *env, jclass _, const jlong addr, const jint rate) {
    reinterpret_cast<TTS *>(addr)->set_rate(rate);
}
