package us.gr8conf

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class ServiceSimulation extends Simulation {

  val config = new ServiceSimulationConfig(ServiceSimulationConfig.loadConfig())
  val feeder = Array(Map("model_id" -> "95833b1e-5f77-11e7-907b-a6006ad3dba0")).circular

  def dataSetup(): Unit = {
    // Here you could do some data setup via HTTP and then build feeders from that data (instead of the dumb hardcoded ID above)
  }

  setUp {
    dataSetup()

    List(
      ServiceScenarios.postModelAndGetModel().inject(
        rampUsersPerSec(config.postModelAndGetModel.rampUsersPerSec) to config.postModelAndGetModel.rampUsersTo during config.rampUpPeriod.seconds,
        constantUsersPerSec(config.postModelAndGetModel.rampUsersTo) during config.duration
      ).protocols(http.baseURL(config.url)),
      ServiceScenarios.getModel(feeder).inject(
        rampUsersPerSec(config.getModel.rampUsersPerSec) to config.getModel.rampUsersTo during config.rampUpPeriod.seconds,
        constantUsersPerSec(config.getModel.rampUsersTo) during config.duration
      ).protocols(http.baseURL(config.url))
    )
  }
}
