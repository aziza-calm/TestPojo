package generator;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;

public class Pojo {
    public String clientPin;
    public BigDecimal reqAmt;
    public String merchant;
    public String uTime;

    public String toString() {
        return new StringJoiner(", ","{", "}")
                .add("\"clientPin\":\"" + clientPin + '"')
                .add("\"reqAmt\":\"" + reqAmt + "\"")
                .add("\"merchant\":\"" + merchant + "\"")
                .add("\"uTime\":\"" + uTime + "\"")
                .toString();
    }
}