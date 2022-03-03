package FileCopy;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileCopy {
    public static void main(String[] args) {
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:input_box?noop=true")
                    .to("file:output_box");
                }
            });
            //inifinite loop so that it be continuous
            while(true)
                camelContext.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
