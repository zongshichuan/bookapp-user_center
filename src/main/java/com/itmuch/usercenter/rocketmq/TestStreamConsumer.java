package com.itmuch.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestStreamConsumer {

//    @StreamListener(Sink.INPUT)
    public void receive(String messageBody){
        log.info("通过stream收到了消息：messageBody={}",messageBody);
    }

//    @StreamListener(value = Sink.INPUT,condition = "headers['my-header']=='myheader'")
//    public void receiveCondition(String messageBody){
//        log.info("receiveCondition-通过stream收到了消息：messageBody={}",messageBody);
//        throw new IllegalArgumentException("消费消息异常!");
//    }
//    @StreamListener(value = Sink.INPUT,condition = "headers['my-header']=='myheader2'")
//    public void receiveCondition2(String messageBody){
//        log.info("receiveCondition2-通过stream收到了消息：messageBody={}",messageBody);
//    }

    /**
     * 全局异常处理
     * @param message  发生异常的消息
     */
//    @StreamListener("errorChannel")
    public void error(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        System.out.println("Handling ERROR: " + errorMessage);
    }

}
