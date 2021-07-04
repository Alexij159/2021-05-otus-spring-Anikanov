package anikan.homework.service;

import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private final LocalizedIOService localizedIOService;

    public UserServiceImpl(LocalizedIOService localizedIOService) {
        this.localizedIOService = localizedIOService;
    }

    @Override
    public String welcomeUser() {
        localizedIOService.println("tester.greeting");
        localizedIOService.println("tester.name-question");
        return localizedIOService.readLine();
    }

}
