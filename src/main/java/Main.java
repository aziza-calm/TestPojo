import org.apache.commons.configuration.ConfigurationException;

public class Main {
    public static void main(String[] args) throws ConfigurationException {
        PojoGenerator generator = new PojoGenerator();

        System.out.println(generator.getPojo().toString());
        System.out.println(generator.getPojo().toString());
        System.out.println(generator.getPojo().toString());
    }
}
