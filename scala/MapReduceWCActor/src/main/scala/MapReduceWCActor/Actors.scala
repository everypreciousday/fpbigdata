package MapReduceWCActor

import akka.actor.{ Actor, ActorSystem, ActorRef, ActorLogging, Props, PoisonPill }
import collection.mutable.{HashMap, Map}
import scala.io.Source

case class WordCountMap(word:String, count:Integer)
case class WordNum(num:Integer)

class MapperActor(shufflerActor: ActorRef) extends Actor with ActorLogging {
   override def receive: Receive = {
     case filePath:String  => {
       val lines = Source.fromFile(filePath).getLines.toList
       val words = lines.flatMap(x => x.split(" "))
       shufflerActor ! WordNum(words.size)
       words.map( x => shufflerActor ! WordCountMap(x, 1))
       context.stop(self)
     }
   }
}

class ShufflerActor(reducerActorList: List[ActorRef]) extends Actor with ActorLogging {
   var receivedWordNum = 0
   override def receive: Receive = {
     case WordNum(num:Integer) => {
        receivedWordNum += num
     }
     case WordCountMap(word: String, count:Integer) => {
       val targetReducer = Math.abs(word.hashCode() % 2)
       println(targetReducer)
       reducerActorList(targetReducer) ! WordCountMap(word, count)
     }
     case "finished" => {
        receivedWordNum -= 1
        if (receivedWordNum == 0) {
          sender ! "print"
        }
     }
   }
 }

class ReducerActor() extends Actor with ActorLogging {
   val resultMap = new HashMap[String, Int]();

   override def receive: Receive = {
     case "print" => {
       println(resultMap)
       context.system.terminate()
     }
     case WordCountMap(word:String, count:Integer) => {
       resultMap.put(word, resultMap.getOrElse(word, 0)+1)
       sender ! "finished"
     }
   }
}

object Main {
     def main(args: Array[String]) {
       val system = ActorSystem("actorsystem")

       val reducerActor1 = system.actorOf(Props(classOf[ReducerActor]), "reducerActor1")
       val reducerActor2 = system.actorOf(Props(classOf[ReducerActor]), "reducerActor2")

       val shufflerActor = system.actorOf(Props(classOf[ShufflerActor],List(reducerActor1, reducerActor2)), "shufflerActor")

       val mapperActor1 = system.actorOf(Props(classOf[MapperActor], shufflerActor), "mapperActor1")
       val mapperActor2 = system.actorOf(Props(classOf[MapperActor], shufflerActor), "mapperActor2")

mapperActor1! "build.gradle"
       mapperActor2! "src/main/scala/MapReduceWCActor/Actors.scala"

     }
 }
