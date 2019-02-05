package SimpleActor

 import akka.actor.{ ActorSystem, Actor, ActorRef, Props }
 import akka.event.Logging

 class JackActor extends Actor {
   val log = Logging(context.system, this)

   def receive = {
     case "Hey" => log.info("Hi")
     case _     => log.info("What?")
   }
 }

 object Main {
     def main(args: Array[String]) {
       val system = ActorSystem("actorsystem")

       val actorJack = system.actorOf(Props(classOf[JackActor]), "actorJack")

       println(s"$actorJack")

       actorJack ! "Hey"
       actorJack ! "Still work for CTU?"
     }
}

