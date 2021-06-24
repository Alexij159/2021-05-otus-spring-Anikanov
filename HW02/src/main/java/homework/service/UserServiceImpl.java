package homework.service;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final IOService ioService;

    public UserServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public String welcomeUser() {
        ioService.println("Добро пожаловать на тест!");
        ioService.println("Как Вас зовут?");
        return ioService.readLine();
    }

}
