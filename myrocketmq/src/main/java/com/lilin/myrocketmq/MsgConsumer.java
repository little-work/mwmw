package com.lilin.myrocketmq;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Slf4j
public class MsgConsumer {


    public static void main(String[] args) {
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer("PushConsumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("PushTopic", "push");

            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(
                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener((MessageListenerConcurrently) (list, Context) -> {
                Message msg = list.get(0);

                String topic = msg.getTopic();
                log.info("topic = " + topic);
                byte[] body = msg.getBody();
                        log.info("body:  " + new String(body));
                String keys = msg.getKeys();
                        log.info("keys = " + keys);
                String tags = msg.getTags();
                        log.info("tags = " + tags);
                        log.info("-----------------------------------------------");

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            );
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
