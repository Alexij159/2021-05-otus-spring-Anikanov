package anikan.homework.service;

import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;


@Service
public class UserServiceImpl implements UserService {
    private final IOService ioService;
    private final LocaleMessagesSource localeMessagesSource;

    public UserServiceImpl(IOService ioService, LocaleMessagesSource localeMessagesSource) {
        this.ioService = ioService;
        this.localeMessagesSource = localeMessagesSource;
    }

    @Override
    public String welcomeUser() {
        ioService.println(localeMessagesSource.getMessage("tester.greeting"));
        ioService.println(localeMessagesSource.getMessage("tester.name-question"));
        String name = ioService.readLine();
        if (isNull(name) || "".equals(name))
            return "Unknown user";
        return name;
    }

}