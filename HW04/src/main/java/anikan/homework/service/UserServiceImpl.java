package anikan.homework.service;

import anikan.homework.domain.User;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;


@Service
public class UserServiceImpl implements UserService {
    private final LocalizedIOService localizedIOService;

    public UserServiceImpl(LocalizedIOService localizedIOService) {
        this.localizedIOService = localizedIOService;
    }

    @Override
    public User welcomeUser() {
        localizedIOService.localizedPrintln("tester.greeting");
        localizedIOService.localizedPrint("tester.name-question");
        String name =  localizedIOService.readLine();
        if ("".equals(name) || isNull(name) ) {
            return new User("Unknown user");
        }
        return new User(name);
    }


}
