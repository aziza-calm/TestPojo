import org.apache.commons.configuration.ConfigurationException;

public class Main {
    public static void main(String[] args) throws ConfigurationException {
        Pojo pojo = new Pojo();
        PojoGenerator generator = new PojoGenerator();
        generator.setPojo(pojo);

        System.out.println(pojo.toString());
    }
}
