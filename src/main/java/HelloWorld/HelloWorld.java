package HelloWorld;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author shem.mwangi
 */
public class HelloWorld {
    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new HelloWorldRoute());
            camelContext.start();
        }
    }
}
