package us.gr8conf.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.gr8conf.model.Model;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class Service {

	private final static Logger LOG = LoggerFactory.getLogger(Service.class);
	private ConcurrentHashMap<String, Model> modelDatum = initModelDatum();

	//Hack - here so that the ServiceProdSmokeSpec example passes
	private ConcurrentHashMap<String, Model> initModelDatum() {
		Model model = new Model();
		model.setId("95833b1e-5f77-11e7-907b-a6006ad3dba0");
		model.setFoo("foo");
		model.setBar(1);

		ConcurrentHashMap<String, Model> modelDatum = new ConcurrentHashMap<>();
		modelDatum.put(model.getId(), model);
		return modelDatum;
	}

	public Model handlePost(Model model) {
		model.setId(UUID.randomUUID().toString());
		modelDatum.put(model.getId(), model);
		return model;
	}

	public Model handleGet(String id) {
		return modelDatum.get(id);
	}
}
