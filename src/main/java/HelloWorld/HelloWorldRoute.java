package HelloWorld;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author shem.mwangi
 */
public class HelloWorldRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        System.out.println("Simple hello world in Apache Camel K.");
    }
}
