package us.gr8conf

import java.util.{Random, UUID}

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

object ServiceScenarios {
  import ServiceActions._

  def getModel(modelIds: FeederBuilder[String]): ScenarioBuilder =
    scenario("Get model by ID")
      .feed(modelIds)
      .exec(getModelAction)

  def postModelAndGetModel(): ScenarioBuilder = {
    scenario("Post model and get model by ID")
      .exec(session =>
        session
          .set("model_foo", UUID.randomUUID().toString)
          .set("model_bar", new Random().nextInt(100))
      )
      .exec(postModelAction)
      .exec(getModelAction)
  }
}
