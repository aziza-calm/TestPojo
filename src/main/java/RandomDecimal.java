import java.math.BigDecimal;

public class RandomDecimal {
    BigDecimal max;
    BigDecimal min;

    public RandomDecimal(BigDecimal min, BigDecimal max) {
        this.max = max;
        this.min = min;
    }

    public BigDecimal getDecimal() {
        return max.subtract(min).multiply(BigDecimal.valueOf(Math.random())).add(min);
    }
}
