package com.pm.analytics_service.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient",groupId = "analytics-service")
    public void consumeEvent(ConsumerRecord<String,byte[]>record) throws InvalidProtocolBufferException {
        try {
            byte[] event = record.value(); // Extract the raw byte array
            log.info("Arrived at offset: {}", record.offset());

            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            log.info("Received Patient Event: [PatientId={}, PatientName={}, PatientEmail={}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());

        } catch (InvalidProtocolBufferException e) {
            log.error("Protobuf deserialization failed. Is the producer sending valid Protobuf? Error: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error in consumer: ", e);
        }
    }


}
