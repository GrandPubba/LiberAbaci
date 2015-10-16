package liber.abaci;

import liber.abaci.config.FibonacciSequenceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(FibonacciSequenceConfig.class)
public class LiberAbaci {

    public static void main(String[] args) {
        SpringApplication.run(LiberAbaci.class, args);
    }
}
