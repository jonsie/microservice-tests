package us.gr8conf;

import com.google.inject.Stage;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smartthings.dw.guice.DwGuice;
import smartthings.dw.guice.EnvironmentModule;

public class Application extends io.dropwizard.Application<DropwizardConfiguration> {

	private final static Logger LOG = LoggerFactory.getLogger(Application.class);

	@Override
	public String getName() {
		return "application";
	}

	@Override
	public void run(DropwizardConfiguration configuration, Environment environment) throws Exception {
		DwGuice.from(Stage.PRODUCTION,
				new EnvironmentModule(environment),
				new ApplicationModule(configuration)
		).register(environment);
	}

	public static void main(String[] args) throws Exception {
		new Application().run(args);
	}
}
