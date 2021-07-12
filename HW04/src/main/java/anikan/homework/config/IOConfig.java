package anikan.homework.config;

import anikan.homework.service.IOService;
import anikan.homework.service.StreamsIOService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IOConfig {
    @Bean("consoleService")
    public IOService getIOService(){
        return new StreamsIOService(System.in, System.out);
    }
}
