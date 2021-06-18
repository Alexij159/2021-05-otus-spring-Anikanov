package homework.service;

import org.springframework.stereotype.Component;

@Component
public class TesterRunner {

    private final TesterService testerService;

    public TesterRunner(TesterService testerService) {
        this.testerService = testerService;
    }


    public void run() {
        testerService.executeTest();
    }

}
