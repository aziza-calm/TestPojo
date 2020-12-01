package generator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.IOException;
import java.math.BigDecimal;

public class PojoGenerator {
    RandomDecimal randomDecimal;
    RandomDicString randomClientPin;
    RandomDicString randomMerchant;
    CurDate curDate;

    public PojoGenerator() throws ConfigurationException {
        Configuration configuration = getConfig();
        BigDecimal max = configuration.getBigDecimal("req.amt.max");
        BigDecimal min = configuration.getBigDecimal("req.amt.min");
        Long timeDiv = configuration.getLong("time.div");
        String pinPath = configuration.getString("dic.client.pin");
        String merchantPath = configuration.getString("dic.merchant");

        this.randomDecimal = new RandomDecimal(max, min);
        this.randomClientPin = new RandomDicString(pinPath);
        this.randomMerchant = new RandomDicString(merchantPath);
        this.curDate = new CurDate(timeDiv);
    }

    private static PropertiesConfiguration getConfig() throws ConfigurationException {
        String filename = System.getProperty("app_config");

        return new PropertiesConfiguration(filename);
    }

    public Pojo getPojo() throws IOException {
        Pojo pojo = new Pojo();
        pojo.clientPin = randomClientPin.getSting();
        pojo.reqAmt = randomDecimal.getDecimal();
        pojo.merchant = randomMerchant.getSting();
        pojo.uTime = curDate.getDate();
        return pojo;
    }
}
