import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.math.BigDecimal;
import java.util.Date;

public class PojoGenerator {
    private static PropertiesConfiguration getConfig() throws ConfigurationException {
        String filename = System.getProperty("app_config");

        return new PropertiesConfiguration(filename);
    }

    public Pojo getPojo() throws ConfigurationException {
        Configuration configuration = getConfig();
        Pojo pojo = new Pojo();
        pojo.clientPin = configuration.getString("client.pin");
        BigDecimal max = configuration.getBigDecimal("req.amt.max");
        BigDecimal min = configuration.getBigDecimal("req.amt.min");
        pojo.reqAmt = max.subtract(min).multiply(BigDecimal.valueOf(Math.random())).add(min);
        pojo.merchant = configuration.getString("merchant");
        pojo.uTime = new Date(System.currentTimeMillis() + configuration.getLong("time.div"));
        return pojo;
    }
}
