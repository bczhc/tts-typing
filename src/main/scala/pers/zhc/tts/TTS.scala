package pers.zhc.tts

import pers.zhc.tts.TTS.SpeakPriority

class TTS {
  private val ttsAddr = JNI.TTS.create()

  def speak(text: String): Unit = {
    println(ttsAddr)
    JNI.TTS.speak(ttsAddr, text)
  }

  def asyncSpeak(text: String): Unit ={
    JNI.TTS.asyncSpeak(ttsAddr, text)
  }

  override def finalize(): Unit = {
    JNI.TTS.release(ttsAddr)
  }

  def setPriority(priority: SpeakPriority): Unit = {
    JNI.TTS.setPriority(ttsAddr, priority.nativePriority)
  }

  /**
   * set voice rate
   *
   * @param rate range: [-10, 10]
   */
  def setRate(rate: Int): Unit = {
    JNI.TTS.setRate(ttsAddr, rate)
  }
}

object TTS {
  abstract case class SpeakPriority() {
    val nativePriority: Int
  }

  case object SpeakPriority {
    object Normal extends SpeakPriority {
      override val nativePriority: Int = 0
    }

    object Alert extends SpeakPriority {
      override val nativePriority: Int = 1
    }

    object Over extends SpeakPriority {
      override val nativePriority: Int = 2
    }
  }
}
