package SQLOpersations;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;

import java.util.Map;

/**
 * @author shem.mwangi
 */
public class SqlOperation {

    public static void main(String[] args) throws Exception {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/apache_camel");
        dataSource.setUser("root");
        dataSource.setPassword("${password}");

        CamelContext context = new DefaultCamelContext();

        context.getRegistry().bind("myDataSource", dataSource);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("direct:start")
                        .to("jdbc:myDataSource")
                        .bean(new ResultHandler(), "printResult");

            }
        });
        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "select * from customer");
    }

}
