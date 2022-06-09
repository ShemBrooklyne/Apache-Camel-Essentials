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
        SimpleRegistry registry = new SimpleRegistry();
        //registry.put("service", service);

        CamelContext context = new DefaultCamelContext(registry);
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("bean:service?method=doSomething");
            }
        });
        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "Hello! Learning Apache camel is fun!");
    }

}
