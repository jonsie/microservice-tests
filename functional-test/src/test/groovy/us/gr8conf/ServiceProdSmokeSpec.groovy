package us.gr8conf

import retrofit2.Response
import us.gr8conf.client.model.Model
import us.gr8conf.tags.ProdSmoke

@ProdSmoke
class ServiceProdSmokeSpec extends FunctionalTestSpecification {

    def "get model by id"() {
        given:
        String id = "95833b1e-5f77-11e7-907b-a6006ad3dba0"

        when:
        Response<Model> getResponse = service.get(id).execute()
        Model getResponseModel = getResponse.body()

        then:
        getResponse.isSuccessful()
        getResponse.code() == 200
        getResponseModel.foo
        getResponseModel.bar
        getResponseModel.id
    }
}
