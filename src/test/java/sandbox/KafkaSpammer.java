package sandbox;

import io.github.rinmalavi.model.SignalValue;
import io.github.rinmalavi.model.TelemetryDataRaw;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class KafkaSpammer {

    private static final Logger log = LoggerFactory.getLogger(KafkaSpammer.class);

    static final String serverUrl = "localhost:43210";
    static final String topicName = "telemetry_data";

    public static void main(String[] args) {
        for (long i = 0; i < 300; i++) {
            sendMessage("" + i, topicName, getTrd(i));
        }
    }

    private static TelemetryDataRaw getTrd(long i) {
        return new TelemetryDataRaw("1", i, makeSignalValues(i));
    }

    private static Map<String, Double> makeSignalValues(long i) {
        Map<String, Double> sv = new HashMap<>();
        if (new Random().nextInt(10) %10 == 0) {
            sv.put(SignalValue.IS_CHARGING, 1d);
        } else {
            sv.put(SignalValue.ODOMETER, i * 1d);
            sv.put(SignalValue.CURRENT_SPEED, 30d);
            sv.put(SignalValue.DRIVING_TIME, i * 12000d);
        }
        return sv;
    }

    private static void sendMessage(String key, String outgoingTopic, TelemetryDataRaw trd) {
        Producer<String, TelemetryDataRaw> producer = createProducer();
        log.info("Sending message to queue {}", outgoingTopic);
        ProducerRecord<String, TelemetryDataRaw> producerRecord = new ProducerRecord<>(outgoingTopic, key, trd);
        Future<RecordMetadata> futureMetadata = producer.send(producerRecord);
        RecordMetadata recordMetadata = null;
        try {
            recordMetadata = futureMetadata.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println("offset:" + recordMetadata.offset());
    }

    public static Producer<String, TelemetryDataRaw> createProducer() {
        Producer<String, TelemetryDataRaw> producer;
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", serverUrl);
        producerProps.put("acks", "all");
        producerProps.put("key.serializer", StringSerializer.class.getCanonicalName());
        producerProps.put("value.serializer", TelemetryDataRawSerializer.class.getCanonicalName());
        producerProps.put("max.request.size", "1000000000");
        producerProps.put("send.buffer.bytes", "1000000000");
        producerProps.put("buffer.memory", "1000000000");
        producer = new KafkaProducer<>(producerProps);

        return producer;
    }
}
