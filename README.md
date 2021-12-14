## Aggregation service

Accept messages from kafka topic `telemetry_data` in form of raw telemetry data 
and calculates some statistics from them.

### Api

After processing this data is accessible at `current-vehicle-statistics` endpoint as described in `api.yml`.

### Run With

    mvn quarkus:dev

The command will run the server and dev kafka service along with it (in dev).
This "dev kafka" is set up with `quarkus.kafka.devservices.port: 43210` in `application.yml`.
There is an example of sending messages to it in `test/java/sandbox/KafkaSpammer.java`

### Tests

Another way to assert functionality is by running tests

    mvn verify

will run integration test in `src/test/java/io/github/rinmalavi/msg/TelemetryDataListenerTest.java`

### Out Of Order (ooo) Messages

Application is stream processing, 
so by the time ooo message arrives it is outdated for all but max speed.
It could be kept for post-processing.
There could be a large batch of ooo messages possibly containing one whole charge, 
this application ignores this highly unlikely event for now. 

### Parking

App considers parking after stopped for `app.moreThanStopTime` in ms. That is if the vehicle is not charging at that moment. 

### How it's made

    mvn io.quarkus.platform:quarkus-maven-plugin:2.5.2.Final:create \
      -DprojectGroupId=io.github.rinmalavi \
      -DprojectArtifactId=aggregation-service \
      -Dextensions="resteasy-jackson, smallrye-reactive-messaging-kafka, quarkus-config-yaml"
