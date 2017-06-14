package us.gr8conf.resources;

import io.dropwizard.validation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartthings.dw.guice.WebResource;
import us.gr8conf.model.GetData;
import us.gr8conf.model.PostData;
import us.gr8conf.services.Service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class Resource implements WebResource {

	private final static Logger LOG = LoggerFactory.getLogger(Resource.class);

	private final Service service;

	@Inject
	public Resource(Service service) {
		this.service = service;
	}

	@POST
	@Path("/post")
	public Response scheduleLoad(@Validated PostData postData) {
		service.handlePost(postData);
		return Response.noContent().build();
	}

	@GET
	@Path("/get")
	public Response scheduleLoad(@Validated GetData getData) {
		service.handleGet(getData);
		return Response.ok().build();
	}
}
