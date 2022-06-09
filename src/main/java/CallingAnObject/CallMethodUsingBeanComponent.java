package CallingAnObject;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

/**
 * @author shem.mwangi
 */
public class CallMethodUsingBeanComponent {

    public static void main(String[] args) throws Exception {

        Service service = new Service();

        CamelContext context = new DefaultCamelContext();
        context.getRegistry().bind("myService", service);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("bean:myService?method=doSomething");
            }
        });
        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "Hello! Learning Apache camel is fun!");
    }

}
