package us.gr8conf.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.gr8conf.model.Model;
import us.gr8conf.model.PostResponse;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class Service {

	private final static Logger LOG = LoggerFactory.getLogger(Service.class);
	private ConcurrentHashMap<String, Model> modelDatum = new ConcurrentHashMap<>();

	public PostResponse handlePost(Model model) {
		String id = UUID.randomUUID().toString();
		modelDatum.put(id, model);

		PostResponse postResponse = new PostResponse();
		postResponse.setId(id);

		return postResponse;
	}

	public Model handleGet(String id) {
		return modelDatum.get(id);
	}
}
