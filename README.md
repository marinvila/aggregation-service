# How it's made

mvn io.quarkus.platform:quarkus-maven-plugin:2.5.1.Final:create \
 -DprojectGroupId=io.github.rinmalavi \
 -DprojectArtifactId=aggregation-service \
 -DclassName="io.github.rinmalavi.AggregatedDataCtrl" \
 -Dpath="/aggregated_data" \
 -Dextensions="resteasy-jackson, smallrye-reactive-messaging-kafka, quarkus-config-yaml"
