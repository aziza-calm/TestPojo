import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.math.BigDecimal;

public class PojoGenerator {
    private static PropertiesConfiguration getConfig() throws ConfigurationException {
        String filename = System.getProperty("app_config");

        return new PropertiesConfiguration(filename);
    }

    public Pojo getPojo() throws ConfigurationException {
        Configuration configuration = getConfig();
        BigDecimal max = configuration.getBigDecimal("req.amt.max");
        BigDecimal min = configuration.getBigDecimal("req.amt.min");
        Long timeDiv = configuration.getLong("time.div");

        Pojo pojo = new Pojo();
        pojo.clientPin = configuration.getString("client.pin");
        pojo.reqAmt = new RandomDecimal(max, min).getDecimal();
        pojo.merchant = configuration.getString("merchant");
        pojo.uTime = new CurDate(timeDiv).getDate();
        return pojo;
    }
}
