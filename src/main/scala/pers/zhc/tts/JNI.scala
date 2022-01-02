package pers.zhc.tts

object JNI {
  object TTS {
    System.load("C:\\Users\\zhaican\\CLionProjects\\sapi-tts\\cmake-build-debug\\libtts-jni-lib.dll")
    type Addr = Long

    @native def create(): Addr

    @native def speak(addr: Addr, text: String): Unit

    @native def release(addr: Addr): Unit

    @native def setPriority(addr: Addr, priority: Int): Unit

    @native def setRate(addr: Addr, rate: Int): Unit

    @native def asyncSpeak(ttsAddr: Addr, text: String): Unit
  }
}
