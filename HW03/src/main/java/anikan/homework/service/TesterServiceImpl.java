package anikan.homework.service;

import anikan.homework.domain.Question;
import anikan.homework.domain.TestResult;
import anikan.homework.domain.User;
import anikan.homework.config.ScoreConfig;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class TesterServiceImpl implements TesterService {
    private final QuestionService questionService;
    private final ScoreConfig scoreProperties;

    private final UserService userService;
    private final LocalizedIOService localizedIOService;


    public TesterServiceImpl(QuestionService questionService, ScoreConfig scoreProperties, UserService userService, LocalizedIOService localizedIOService) {
        this.questionService = questionService;
        this.scoreProperties = scoreProperties;
        this.localizedIOService = localizedIOService;
        this.userService = userService;
    }

    public void executeTest(){
        String userName = userService.welcomeUser();
        User user = new User(userName);

        showQuestions();

        int score = test(user);
        TestResult testResult = new TestResult(user, score);
        localizedIOService.printfWithParameterizedLocalization("tester.scoring", Collections.singletonList(scoreProperties.getDescriptionFor(testResult.getScore())));
    }


    private void showQuestions() {
        localizedIOService.println("tester.all-questions");
        questionService.getQuestions().forEach(q -> localizedIOService.printfWithoutLocalization("%s. %s\n",q.getId(),q.getWording()));
    }


    private int test(User user) {
        int currentScore = 0;
        for (Question q :questionService.getQuestions()) {
            localizedIOService.printfWithParameterizedLocalization("tester.enter-answer", Arrays.asList(new Object[]{user.getName(), q.getId()}));
            String answer = localizedIOService.readLine();
            if (q.getCorrectAnswer().equalsIgnoreCase(answer)) {
                currentScore++;
            }
        }
        return currentScore;
    }
}
