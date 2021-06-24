package homework.service;

import homework.config.ScoreConfig;
import homework.domain.Question;
import homework.domain.TestResult;
import homework.domain.User;
import org.springframework.stereotype.Service;


@Service
public class TesterService {
    private final QuestionService questionService;
    private final ScoreConfig scoreProperties;
    private final IOService ioService;
    private final UserService userService;


    public TesterService(QuestionService questionService, ScoreConfig scoreProperties, IOService ioService,
                         UserService userService) {
        this.questionService = questionService;
        this.scoreProperties = scoreProperties;
        this.ioService = ioService;
        this.userService = userService;
    }
    private int test(User user) {
        int currentScore = 0;
        for (Question q :questionService.getQuestions()) {
            ioService.printf("%s введите ответ на вопрос №%s%n",
                    user.getName(), q.getId());
            String answer = ioService.readLine();
            if (q.getCorrectAnswer().equalsIgnoreCase(answer))
                currentScore++;
        }
        return currentScore;
    }

    public void showQuestions() {
        ioService.println("Вам предстоит ответить на следующие вопросы:\n");
        questionService.getQuestions().forEach(q -> ioService.printf("%s. %s\n",q.getId(),q.getWording()));
    }

    public void executeTest() {
        String userName = userService.welcomeUser();
        User user = new User(userName);

        showQuestions();
        int score = test(user);
        TestResult testResult = new TestResult(user, score);
        ioService.printf("Вы получаете %s.%n Поздравляем Вас!",scoreProperties.getDescriptionFor(testResult.getScore()));
    }
}
