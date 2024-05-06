package pers.zhc.tts

import pers.zhc.tts.TTS.SpeakPriority

import java.util.concurrent.Executors
import javax.swing.event.{DocumentEvent, DocumentListener}
import javax.swing.{JFrame, JTextArea, WindowConstants}

object Main {
  def main(args: Array[String]): Unit = {
    System.load("C:/bczhc/code/tts-typing/jni/build/libjni_lib.dll")

    val frame = new JFrame()
    frame.setTitle("TTS")
    frame.setSize(500, 300)
    frame.setVisible(true)
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    val ta = new JTextArea()
    ta.setFont(ta.getFont.deriveFont(25F))
    ta.setLineWrap(true)
    frame.add(ta)

    val threadPool = Executors.newCachedThreadPool()

    val wordCombinator = new WordCombinator({ word =>
      threadPool.submit(new Runnable {
        override def run(): Unit = {
          val tts = new TTS
          tts.setPriority(SpeakPriority.Over)
          tts.setRate(2)
          tts.speak(word)
        }
      })
      println(s"Combined: $word")
    })

    ta.getDocument.addDocumentListener(new DocumentListener {
      override def insertUpdate(e: DocumentEvent): Unit = {
        val text = ta.getText
        val changedText = text.substring(e.getOffset, e.getOffset + e.getLength)
        println(changedText)
        wordCombinator.commit(changedText)
        //        if (changedText.charAt(0).toShort > 0xff &&
        //          changedText.charAt(changedText.length - 1) > 0xff /* non-alphabetical */ ) {
        //          wordCombinator.commit(changedText)
        //          println(changedText)
        //        }
      }

      override def removeUpdate(e: DocumentEvent): Unit = {

      }

      override def changedUpdate(e: DocumentEvent): Unit = {

      }
    })
  }
}

class WordCombinator(callback: WordCombinator.Callback) {
  private val stringBuilder = new StringBuilder
  private var timer = new ReusableTimer()

  def commit(char: String): Unit = {
    stringBuilder.append(char)

    timer.cancel()
    timer = new ReusableTimer()
    timer.schedule({ () =>
      callback(stringBuilder.toString())
      stringBuilder.clear()
    }, WordCombinator.INTERVAL)
  }
}

private object WordCombinator {
  private type Callback = String => Unit

  private val INTERVAL = 50
}

class ReusableTimer {
  @volatile private var canceled = false

  def schedule(task: ReusableTimer.Task, delay: Long): Unit = {
    canceled = false
    new Thread({ () =>
      Thread.sleep(delay)
      if (!canceled) {
        task()
      }
    }).start()
  }

  def cancel(): Unit = {
    canceled = true
  }
}

object ReusableTimer {
  private type Task = () => Unit
}
