package generator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.IOException;
import java.math.BigDecimal;

public class PojoGenerator {
    private static PropertiesConfiguration getConfig() throws ConfigurationException {
        String filename = System.getProperty("app_config");

        return new PropertiesConfiguration(filename);
    }

    public Pojo getPojo() throws ConfigurationException, IOException {
        Configuration configuration = getConfig();
        BigDecimal max = configuration.getBigDecimal("req.amt.max");
        BigDecimal min = configuration.getBigDecimal("req.amt.min");
        Long timeDiv = configuration.getLong("time.div");
        String pinPath = configuration.getString("dic.client.pin");
        String merchantPath = configuration.getString("dic.merchant");

        Pojo pojo = new Pojo();
        pojo.clientPin = new RandomDicString(pinPath).getSting();
        pojo.reqAmt = new RandomDecimal(max, min).getDecimal();
        pojo.merchant = new RandomDicString(merchantPath).getSting();
        pojo.uTime = new CurDate(timeDiv).getDate();
        return pojo;
    }
}
