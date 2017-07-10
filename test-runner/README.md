# test-runner

A Gradle example to run Spock test JARs and Gatling JARs.  Typically you would make this a standalone tool - 
i.e., not a sub-project for a specific service.

## Execute functional-test

Once you have the functional-test JAR published to maven local, run the tests with this command:

	./gradlew test-runner:functionalTest \
		-PtestGroup=us.gr8conf \
		-PtestName=functional-test \
		-PtestVersion=0.0.1 \
		-PtestClassifier=tests
		
You can execute the smoke tests by including `-Pprodsmoke` in the above command.

## Execute load-test

Once you have the load-test JAR published to maven local, run the tests with this command:

	./gradlew test-runner:loadTest \
		-PtestGroup=us.gr8conf \
		-PtestName=functional-test \
		-PtestVersion=0.0.1 \
		-PtestClassifier=load
