package us.gr8conf

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

object ServiceSimulationEngine extends App {
  val props = new GatlingPropertiesBuilder
  props.simulationClass(classOf[ServiceSimulation].getName)
  props.resultsDirectory("build/reports/gatling")
  props.binariesDirectory("build/classes/main")

  Gatling.fromMap(props.build)
  sys.exit()
}
