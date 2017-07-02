package us.gr8conf

import groovy.json.JsonOutput
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Response
import us.gr8conf.client.model.Model

class ServiceSpec extends FunctionalTestSpecification {

    def "post model, get model by id"() {
        given:
        Model model = new Model(foo: "test", bar: 123)

        when:
        Response<Model> postResponse = service.post(model).execute()
        Model postResponseModel = postResponse.body()

        then:
        postResponse.isSuccessful()
        postResponseModel.foo == model.foo
        postResponseModel.bar == model.bar
        postResponseModel.id

        and:
        Response<Model> getResponse = service.get(postResponseModel.id).execute()
        Model getResponseModel = postResponse.body()

        then:
        getResponse.isSuccessful()
        getResponse.code() == 200
        getResponseModel.foo == postResponseModel.foo
        getResponseModel.bar == postResponseModel.bar
        getResponseModel.id  == postResponseModel.id
    }

    def "post model, get model by bad id"() {
        given:
        Model model = new Model(foo: "test", bar: 123)

        when:
        Response<Model> postResponse = service.post(model).execute()
        Model postResponseModel = postResponse.body()

        then:
        postResponse.isSuccessful()
        postResponseModel.foo == model.foo
        postResponseModel.bar == model.bar
        postResponseModel.id

        and:
        Response<Model> getResponse = service.get(UUID.randomUUID().toString()).execute()

        then:
        !getResponse.isSuccessful()
        getResponse.code() == 404
    }

    def "post invalid model"() {
        given:
        MediaType JSON = MediaType.parse("application/json; charset=utf-8")
        String json = JsonOutput.toJson([name: 'John Doe', age: 42])
        RequestBody body = RequestBody.create(JSON, json)

        when:
        Response<Model> postResponse = service.post(body).execute()

        then:
        !postResponse.isSuccessful()
        postResponse.code() == 400
    }
}
