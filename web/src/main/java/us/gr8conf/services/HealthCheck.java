package us.gr8conf.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartthings.dw.guice.NamedHealthCheck;

public class HealthCheck extends NamedHealthCheck {
	private final static Logger LOG = LoggerFactory.getLogger(HealthCheck.class);

	@Override
	public String getName() {
		return "healthCheck";
	}

	@Override
	protected Result check() throws Exception {
		LOG.info("Nothing to check yet.");
		return Result.healthy();
	}
}
