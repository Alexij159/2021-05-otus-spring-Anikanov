package anikan.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileNameProvider {
    private final LocaleProvider localeProvider;
    private final String questionsFilePath;
    public FileNameProvider(@Value("${questions.filePath}") String questionsFilePath, LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
        this.questionsFilePath = questionsFilePath;
    }

    public String getQuestionsFilePath() {
        return questionsFilePath + localeProvider.getLocale() + ".csv";
    }
}
