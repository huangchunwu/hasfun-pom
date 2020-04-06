package cn.hasfun.framework.springboot.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafaConsumer {

    private   Logger logger = LoggerFactory.getLogger(KafkaProducer.class);


    @KafkaListener(topics = "${topicName.topic1}")
    public void listen(String data) {
        logger.info(data);
    }
}
