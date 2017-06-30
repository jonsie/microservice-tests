package us.gr8conf.resources;

import io.dropwizard.validation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartthings.dw.guice.WebResource;
import us.gr8conf.model.Model;
import us.gr8conf.services.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Resource implements WebResource {

	private final static Logger LOG = LoggerFactory.getLogger(Resource.class);

	private final Service service;

	@Inject
	public Resource(Service service) {
		this.service = service;
	}

	@POST
	@Path("/post")
	public Response scheduleLoad(@Validated Model model) {
		model = service.handlePost(model);
		return Response.ok(model, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/get/{id}")
	public Response scheduleLoad(@PathParam("id") String id) {
		Model model = service.handleGet(id);
		if (model == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(model, MediaType.APPLICATION_JSON).build();
	}
}
