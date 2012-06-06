import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "logstash-frontend"
    val appVersion      = "1.0-SNAPSHOT"

    val akkazmq = ProjectRef(uri("../akka-zmq"),"") 
    val appDependencies = Seq(
      // Add your project dependencies here,
    )

    val dispatchLiftJson = uri("git://github.com/dispatch/dispatch-lift-json#0.1.1")

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).dependsOn(akkazmq,dispatchLiftJson).aggregate(akkazmq).settings(
      // Add your own project settings here      
    )


}
