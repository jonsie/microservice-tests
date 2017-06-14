package us.gr8conf;

import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartthings.dw.buildinfo.BuildInfoModule;
import smartthings.dw.guice.AbstractDwModule;
import smartthings.dw.logging.LoggingModule;
import us.gr8conf.resources.Resource;
import us.gr8conf.services.HealthCheck;
import us.gr8conf.services.Service;

public class ApplicationModule extends AbstractDwModule {
	private final static Logger LOG = LoggerFactory.getLogger(ApplicationModule.class);
	private DropwizardConfiguration config;

	public ApplicationModule(DropwizardConfiguration config) {
		this.config = config;
	}

	@Override
	protected void configure() {

		// external modules
		install(new LoggingModule());
		install(new BuildInfoModule());

		bind(HealthCheck.class).in(Scopes.SINGLETON);
		bind(Resource.class).in(Scopes.SINGLETON);
		bind(Service.class).in(Scopes.SINGLETON);

		registerResource(Resource.class);
		registerHealthCheck(HealthCheck.class);
	}

	@Provides
	@Singleton
	ApplicationConfiguration provideApplicationConfiguration() {
		return config.getApplication();
	}
}