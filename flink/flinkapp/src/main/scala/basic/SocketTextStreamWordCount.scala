package basic

import org.apache.flink.streaming.api.scala._

import java.lang.System.currentTimeMillis

object SocketTextStreamWordCount {

  def main(args: Array[String]) {
    if (args.length != 1) {
      System.err.println("USAGE:<outputPath>")
      return
    }

    val outputPath= args(0)

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val text = env.socketTextStream("localhost", 9000)
    val wordCountDataStream = text.flatMap { line => line.split("\\s") }.
      map { (_, 1) }.
      keyBy(0).
      sum(1)

    wordCountDataStream.writeAsText(outputPath)

    env.execute("Scala SocketTextStreamWordCount Example")
  }
}
