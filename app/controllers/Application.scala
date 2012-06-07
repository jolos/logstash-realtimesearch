package controllers
import play.api._
import play.api.mvc._
import play.api.mvc.BodyParser
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import play.api.libs.json._

import dispatch.liftjson.Js._
import net.liftweb.json.JsonAST._

import akka.actor._
import akka.util.Timeout
import akka.pattern.ask
import akka.util.duration._
import akka.serialization.SerializationExtension

import jolos.logstashconsumer._
import java.util.Random

object Application extends Controller {

  val system = ActorSystem()
  var master = system.actorOf(Props[Master])
  implicit val timeout = Timeout(1 second)
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def query(index: String) = WebSocket.async[JsValue] { request => 
      // send registerquerymessage to master actor 

      val rand = new Random()
      val identifier = "query"+rand.nextInt()
      // register test query
      (master ? InitQueryMessage(index,identifier)).asPromise.map {
              case QueryInitialised(channel,iteratee) =>
                // Create an Iteratee to consume the feed
                (iteratee,channel)
      }
    }
}
