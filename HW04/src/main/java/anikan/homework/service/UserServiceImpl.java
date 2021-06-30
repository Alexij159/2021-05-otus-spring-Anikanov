package anikan.homework.service;

import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;


@Service
public class UserServiceImpl implements UserService {
    private final LocalizedIOService localizedIOService;

    public UserServiceImpl(LocalizedIOService localizedIOService) {
        this.localizedIOService = localizedIOService;
    }

    @Override
    public String welcomeUser() {
        localizedIOService.localizedPrintln("tester.greeting");
        localizedIOService.localizedPrint("tester.name-question");
        return localizedIOService.readLine();
    }


}
