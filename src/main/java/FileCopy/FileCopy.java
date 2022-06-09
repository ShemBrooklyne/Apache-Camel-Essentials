package FileCopy;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author shem.mwangi
 */
public class FileCopy {
    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
                camelContext.addRoutes(new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        from("file:input_box?noop=true")
                        .to("file:output_box");
                    }
                });
                //infinite loop so that it be continuous
                while(true)
                    camelContext.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
