package com.PMS.notification_service.kafka;

import Patient.events.PatientEvent;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "notification-service")
    public void consumeEvent(byte[] event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("Received patient event from Kafka Producer: [Patient Id: {}, Patient Name: {}, Patient Email: {}]",
                    patientEvent.getPatientId(), patientEvent.getName(), patientEvent.getEmail());
        }catch (InvalidProtocolBufferException e) {
            log.error("Error parsing event from Kafka Producer: {}", e.getMessage());
        }
    }
}
