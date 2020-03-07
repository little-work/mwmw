package com.lilin.myrocketmq;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;


@Slf4j
public class MsgProduct {

    public static void main(String[] args){
        DefaultMQProducer producer = new DefaultMQProducer("Producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        try {
            producer.start();

            for (int i = 0; i < 5; i++) {
                new Thread(() -> {
                    Message msg = new Message("PushTopic",
                            "push",
                            "1",
                            "Just for test.".getBytes());
                    try {
                        SendResult result = producer.send(msg);

                        log.info("id:" + result.getMsgId() +
                                " result:" + result.getSendStatus());
                    } catch (MQClientException e) {
                        e.printStackTrace();
                    } catch (RemotingException e) {
                        e.printStackTrace();
                    } catch (MQBrokerException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}
