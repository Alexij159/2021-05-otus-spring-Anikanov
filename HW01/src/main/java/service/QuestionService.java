package service;


import java.io.IOException;

public interface QuestionService {
    void loadQuestions() throws IOException;
    void showQuestions();
}
