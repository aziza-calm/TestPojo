import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;

public class Pojo {
    String clientPin;
    BigDecimal reqAmt;
    String merchant;
    Date uTime;

    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return new StringJoiner(", ",Pojo.class.getSimpleName() + "[", "]")
                .add("clientPin='" + clientPin + "'")
                .add("reqAmt='" + reqAmt + "'")
                .add("merchant='" + merchant + "'")
                .add("uTime='" + formatter.format(uTime) + "'")
                .toString();
    }
}