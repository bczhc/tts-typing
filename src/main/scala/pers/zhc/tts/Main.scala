package pers.zhc.tts

import pers.zhc.tts.TTS.SpeakPriority
import javax.swing.event.{DocumentEvent, DocumentListener}
import javax.swing.{JFrame, JTextArea, WindowConstants}

object Main {
  def main(args: Array[String]): Unit = {
    val frame = new JFrame()
    frame.setTitle("TTS")
    frame.setSize(500, 300)
    frame.setVisible(true)
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

    val ta = new JTextArea()
    ta.setFont(ta.getFont.deriveFont(25F))
    ta.setLineWrap(true)
    frame.add(ta)

    val wordCombinator = new WordCombinator({ word =>
      val tts = new TTS
      tts.setPriority(SpeakPriority.Over)
      tts.asyncSpeak(word)
    })

    ta.getDocument.addDocumentListener(new DocumentListener {
      override def insertUpdate(e: DocumentEvent): Unit = {
        val text = ta.getText
        val changedText = text.substring(e.getOffset, e.getOffset + e.getLength)
        println(changedText)
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

object WordCombinator {
  type Callback = String => Unit

  private val INTERVAL = 50
}

class ReusableTimer {
  @volatile private var canceled = false

  def schedule(task: ReusableTimer.Task, delay: Long): Unit = {
    canceled = false
    println(1)
    new Thread({ () =>
      Thread.sleep(delay)
      if (!canceled) {
        task()
      }
    }).start()
    println(2)
  }

  def cancel(): Unit = {
    canceled = true
  }
}

object ReusableTimer {
  type Task = () => Unit
}
