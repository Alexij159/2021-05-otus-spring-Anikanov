package anikan.homework.shell;

import anikan.homework.domain.User;
import anikan.homework.service.TesterService;
import anikan.homework.service.UserService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class TestHoldingCommand {

    private User user;
    private final TesterService testerService;
    private final UserService userService;

    public TestHoldingCommand(TesterService testerService, UserService userService) {
        this.testerService = testerService;
        this.userService = userService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login() {
        this.user = new User(userService.welcomeUser());
        return String.format("Добро пожаловать: %s", user.getName());
    }

    @ShellMethod(value = "Begin test command", key = {"b", "begin", "begin test"})
    @ShellMethodAvailability(value = "isBeginTestCommandAvailable")
    public String beginTest() {
        testerService.executeTest(user);
        return "\n Тест завершен";
    }

    private Availability isBeginTestCommandAvailable() {
        return user == null? Availability.unavailable("Сначала залогиньтесь"): Availability.available();
    }
}
