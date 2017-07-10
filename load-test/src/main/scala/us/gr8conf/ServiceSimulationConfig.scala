package us.gr8conf

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

object ServiceSimulationConfig {
  def loadConfig(): Config = {
    val configFilePath = sys.props.get("simulation.config")

    if (configFilePath.isDefined) {
      val file = new File(configFilePath.get)
      ConfigFactory.parseFile(file)
    } else {
      ConfigFactory.parseResources("simulation.conf")
    }
  }

}

class ServiceSimulationConfig(config: Config) {

  val url = config.getString("service.url")
  val rampUpPeriod = config.getInt("service.rampUpPeriod")
  val duration = config.getInt("service.duration")

  val getModel = new {
    val rampUsersPerSec = config.getInt("service.getModel.rampUsersPerSec")
    val rampUsersTo = config.getInt("service.getModel.rampUsersTo")
  }

  val postModelAndGetModel = new {
    val rampUsersPerSec = config.getInt("service.postModelAndGetModel.rampUsersPerSec")
    val rampUsersTo = config.getInt("service.postModelAndGetModel.rampUsersTo")
  }
}
