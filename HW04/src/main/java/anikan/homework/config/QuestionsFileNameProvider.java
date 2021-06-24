package anikan.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QuestionsFileNameProvider {
    private final String questionsFileName;

    public QuestionsFileNameProvider(@Value("${questions.filePath}") String questionsFileName, LocaleProvider localeProvider) {
        this.questionsFileName = questionsFileName + localeProvider.getLocale() + ".csv";
    }

    public String getQuestionsFileName() {
        return questionsFileName;
    }
}
