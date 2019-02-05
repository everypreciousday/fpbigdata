 package PingPongActor

 import akka.actor.{ Actor, ActorSystem, ActorRef, ActorLogging, Props, PoisonPill }
 import scala.concurrent.duration._

 class JackActor extends Actor with ActorLogging {
   var cnt = 0

   override def receive: Receive = {
     case "Ping" => {
       log.info(s"${self.path} recieved Ping ${cnt}")
       cnt = cnt + 1
       if (cnt == 10) {
         sender() ! PoisonPill
         context.stop(self)
       }
       else {
         sender() ! "Pong"
       }

     }
     case _ => throw new Exception("I failed")
   }
 }


 class TonyActor(peer: ActorRef) extends Actor with ActorLogging {
   override def receive: Receive = {
     case "Ping" => {
       log.info(s"${self.path} received ping")
       peer ! "Ping"
     }
     case "Pong" => {
       log.info(s"${self.path} received pong")
       sender() ! "Ping"
     }
     case _ => throw new Exception("I failed")
   }
 }

 object Main {
     def main(args: Array[String]) {
       val system = ActorSystem("actorsystem")

       val jackActor= system.actorOf(Props(classOf[JackActor]), "JackBauer")
       val tonyActor = system.actorOf(Props(classOf[TonyActor], jackActor), "TonyAlmeida")

       tonyActor ! "Ping"
     }
 }
