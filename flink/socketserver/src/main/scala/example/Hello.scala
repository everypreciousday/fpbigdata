package example

import java.net.{Socket, ServerSocket}
import java.util.concurrent.{Executors, ExecutorService}
import java.io.PrintStream

class Handler(socket: Socket) extends Runnable {
  def run() {
    val out = new PrintStream(socket.getOutputStream)
        while(true) {
            out.println("hello")
            out.flush
            Thread.sleep(1000)
        }
  }
}

object Hello {
  def main(args: Array[String]):Unit = {
    val port = 9000
    val poolSize = 10
    val server = new ServerSocket(port)

    val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

    try{
      println("server waiting in 9000...")
      while (true) {
        val socket = server.accept
        println("connected")
        pool.execute(new Handler(socket))
      }
    }
    catch {
      case e: Exception => println(e.getStackTrace)
    }
  }
}
