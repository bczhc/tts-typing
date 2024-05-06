package pers.zhc.tts

import pers.zhc.tts.TTS.SpeakPriority

class TTS {
  private val ttsAddr = JNI.create()

  def speak(text: String): Unit = {
    println(ttsAddr)
    JNI.speak(ttsAddr, text)
  }

  override def finalize(): Unit = {
    JNI.release(ttsAddr)
  }

  def setPriority(priority: SpeakPriority): Unit = {
    JNI.setPriority(ttsAddr, priority.nativePriority)
  }

  /**
   * set voice rate
   *
   * @param rate range: [-10, 10]
   */
  def setRate(rate: Int): Unit = {
    JNI.setRate(ttsAddr, rate)
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

  object Flags {
    val SPF_DEFAULT = 0
    val SPF_ASYNC = 1
  }
}
