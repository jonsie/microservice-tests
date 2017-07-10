package us.gr8conf

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object ServiceActions {

  def getModelAction: HttpRequestBuilder =
    http("Get model")
      .get("/get/${model_id}")
      .check(status is 200)

  def postModelAction: HttpRequestBuilder =
    http("Post model")
      .post("/post")
      .header("Content-Type", "application/json")
      .body(StringBody("""{"foo": "${model_foo}", "bar": ${model_bar}}"""))
      .check(status is 200)
      .check(jsonPath("$..id").saveAs("model_id"))
}
