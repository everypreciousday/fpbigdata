package WordCountActor

import akka.actor.{ Actor, ActorSystem, ActorRef, ActorLogging, Props, PoisonPill }
import scala.concurrent.duration._
import collection.mutable.{HashMap, Map}
import scala.util.Random

case class Line(line: String)

class WordEmitActor(wordCountActor: ActorRef) extends Actor with ActorLogging {
     def makeRandomStr():String = {
         Random.alphanumeric.take(4).mkString
     }

   override def receive: Receive = {
     case "emit" => {
       wordCountActor ! Line(makeRandomStr)
     }
   }
 }

class WordCountActor() extends Actor with ActorLogging {
   val resultMap = new HashMap[String, Int]();
   override def receive: Receive = {
     case "print" => {
       println(resultMap)
     }
     case Line(line) => {
       line.split(" ").foreach(word => {
         resultMap.put(word, resultMap.getOrElse(word, 0)+1)
       })
     }
   }
 }

 object Main {
     def main(args: Array[String]) {
       val system = ActorSystem("actorsystem")

       val wordCountActor = system.actorOf(Props(classOf[WordCountActor]), "wordCountActor")
       val wordEmitActor = system.actorOf(Props(classOf[WordEmitActor], wordCountActor), "wordEmitactor")

       import system.dispatcher

       val cancellable = system.scheduler.schedule(
           0 milliseconds,
           50 milliseconds,
           wordEmitActor,
           "emit")
       Thread.sleep(1000)
       wordCountActor ! "print"
       cancellable.cancel()
       system.terminate()
     }
 }
