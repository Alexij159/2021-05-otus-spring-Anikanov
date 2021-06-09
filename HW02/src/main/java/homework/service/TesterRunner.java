package homework.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TesterRunner implements ApplicationRunner {

    private final TesterService testerService;

    public TesterRunner(TesterService testerService) {
        this.testerService = testerService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        testerService.executeTest();
    }

}
