package us.gr8conf;

import io.dropwizard.Configuration;

public class DropwizardConfiguration extends Configuration {
	private ApplicationConfiguration application = new ApplicationConfiguration();

	public ApplicationConfiguration getApplication() {
		return application;
	}

	public void setAtlantic(ApplicationConfiguration application) {
		this.application = application;
	}
}
