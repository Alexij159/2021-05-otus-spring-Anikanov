package anikan.homework.service;

import anikan.homework.config.ScoreConfig;
import anikan.homework.domain.Question;
import anikan.homework.domain.TestResult;
import anikan.homework.domain.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class TesterServiceImpl implements TesterService {
    private final QuestionService questionService;
    private final ScoreConfig scoreProperties;

    private final LocalizedIOServiceImpl localizedIOService;


    public TesterServiceImpl(QuestionService questionService, ScoreConfig scoreProperties, LocalizedIOServiceImpl localizedIOService) {
        this.questionService = questionService;
        this.scoreProperties = scoreProperties;
        this.localizedIOService = localizedIOService;
    }

    @Override
    public void executeTest(User user){
        showQuestions();

        int score = test(user);
        TestResult testResult = new TestResult(user, score);
        localizedIOService.localizedPrintfWithLocalizationParams("tester.scoring", Collections.singletonList(scoreProperties.getDescriptionFor(testResult.getScore())));
    }


    private void showQuestions() {
        localizedIOService.localizedPrintln("tester.all-questions");
        questionService.getQuestions().forEach(q -> localizedIOService.printf("%s. %s\n",q.getId(),q.getWording()));
    }


    private int test(User user) {
        int currentScore = 0;
        for (Question q :questionService.getQuestions()) {
            localizedIOService.localizedPrintfWithLocalizationParams("tester.enter-answer", user.getName(), q.getId());
            String answer = localizedIOService.readLine();
            if (q.getCorrectAnswer().equalsIgnoreCase(answer))
                currentScore++;
        }
        return currentScore;
    }
}
