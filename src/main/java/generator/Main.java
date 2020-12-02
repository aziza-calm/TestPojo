package generator;

import org.apache.commons.configuration.ConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ConfigurationException, IOException {
        PojoGenerator generator = new PojoGenerator();

        System.out.println(generator.getPojo().toString());
        System.out.println(generator.getPojo().toString());
        System.out.println(generator.getPojo().toString());
        System.out.println(generator.getPojo().toString());
        System.out.println(generator.getPojo().toString());
        System.out.println(generator.getPojo().toString());
        System.out.println(generator.getPojo().toString());
    }
}
