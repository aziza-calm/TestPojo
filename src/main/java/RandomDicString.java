import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class RandomDicString {
    String filename;

    public RandomDicString(String filename) {
        this.filename = filename;
    }

    public String getSting() throws IOException {
        List<String> pins = Files.readAllLines(Paths.get(filename));
        return pins.get(new Random().nextInt(pins.size()));
    }
}
