package us.gr8conf.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.gr8conf.model.GetData;
import us.gr8conf.model.PostData;


public class Service {

	private final static Logger LOG = LoggerFactory.getLogger(Service.class);

	public void handlePost(PostData postData) {
		LOG.info("Post");
	}

	public void handleGet(GetData getData) {
		LOG.info("Get");
	}
}
