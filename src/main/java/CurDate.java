import java.util.Date;

public class CurDate {
    Long timeDiv;

    public CurDate(Long timeDiv) {
        this.timeDiv = timeDiv;
    }

    public Date getDate() {
        return new Date(System.currentTimeMillis() + this.timeDiv);
    }
}
