package us.gr8conf.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.gr8conf.model.Model;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class Service {

	private final static Logger LOG = LoggerFactory.getLogger(Service.class);
	private ConcurrentHashMap<String, Model> modelDatum = new ConcurrentHashMap<>();

	public Model handlePost(Model model) {
		model.setId(UUID.randomUUID().toString());
		modelDatum.put(model.getId(), model);
		return model;
	}

	public Model handleGet(String id) {
		return modelDatum.get(id);
	}
}
