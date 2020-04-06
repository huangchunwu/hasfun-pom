package cn.hasfun.framework.springboot.controller;

import cn.hasfun.framework.springboot.constant.ExecResult;
import cn.hasfun.framework.springboot.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/kafka")
@RestController
public class KafkaController {

    @Autowired
    KafkaProducer kafkaProducer;

    @RequestMapping("/send")
    public ExecResult sendMsg(String topic,String msg){
        kafkaProducer.sendMsg(topic,msg);
        return ExecResult.builder().message("发送成功").build();
    }
}
