package io.github.rinmalavi.msg;

import io.github.rinmalavi.model.TelemetryDataRaw;
import io.github.rinmalavi.vehicle.StatsProcessor;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class TelemetryDataListener {

    private static final Logger log = LoggerFactory.getLogger(TelemetryDataListener.class);

    @Inject
    StatsProcessor vehicleStateProcessor;

    @Incoming("ra-telemetry-data")
    public CompletionStage<Void> onMsg(Message<Optional<TelemetryDataRaw>> msg) {
        String msgId = msg.getMetadata().get(IncomingKafkaRecordMetadata.class).map(IncomingKafkaRecordMetadata::getKey).orElse("missing key").toString();
        Optional<TelemetryDataRaw> data = msg.getPayload();
        data.ifPresentOrElse(
                vehicleStateProcessor::processTelemetry,
                () -> log.warn("Message {} was unreadable", msgId)
        );
        return msg.ack();
    }
}
