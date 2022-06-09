package ProducerConsumer;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class ProducerConsumer {

    public static void main(String[] args) throws Exception {
        try (CamelContext context = new DefaultCamelContext()) {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:start")
                            .process(new Processor() {
                                @Override
                                public void process(Exchange exchange) throws Exception {
                                    String message = exchange.getIn().getBody(String.class);
                                    message = message + "- By Shem B. Mwangi";

                                    exchange.getOut().setBody(message);

                                }
                            })
                            .to("seda:end");
                }
            });

            context.start();

            ProducerTemplate producerTemplate = context.createProducerTemplate();
            producerTemplate.sendBody("direct:start", "Hello guys!");

            ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
            String message = consumerTemplate.receiveBody("seda:end", String.class);
            System.out.println(message);
        }
    }

}
