package homework.config;

import homework.service.IOService;
import homework.service.StreamsIOService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IOConfig {
    @Bean("consoleService")
    public IOService getIOService(){
        return new StreamsIOService(System.in, System.out);
    }
}
