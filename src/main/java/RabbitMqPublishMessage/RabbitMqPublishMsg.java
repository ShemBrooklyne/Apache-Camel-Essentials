package RabbitMqPublishMessage;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.nio.charset.StandardCharsets;

/**
 * @author shem.mwangi
 */
public class RabbitMqPublishMsg {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        //rabbitMQ configurations
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("camel-k-exchange", "direct", true);
        channel.queueDeclare("camel-k", true, false, false, null);
        channel.queueBind("camel-k", "camel-k-exchange", "camel-k-dlq");

        channel.basicPublish(
                "camel-k-exchange",
                "camel-k-dlq",
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                "I honestly don't know what am doing".getBytes(StandardCharsets.UTF_8));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("rabbitmq:localhost:5672/camel-k-exchange?username=guest&password=guest&autoDelete=false&routingKey=camel-k-dlq&queue=camel-k")
                        .to("mock:result");
            }
        });
        context.start();
    }
}
