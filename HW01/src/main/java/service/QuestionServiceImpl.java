package service;

import dao.QuestionDao;
import domain.Question;


import java.io.IOException;


public class QuestionServiceImpl implements QuestionService {

    private QuestionLoader questionLoader;
    private QuestionDao dao;


    public QuestionServiceImpl(QuestionLoader questionLoader, QuestionDao dao) {
        this.questionLoader = questionLoader;
        this.dao = dao;
    }

    @Override
    public void loadQuestions() throws IOException {
        questionLoader.loadQuestions();
    }

    @Override
    public void showQuestions() {
        System.out.println("Вам предстоит ответить на следующие вопросы:\n");
        dao.getAll().forEach(this::showQuestion);
    }

    private void showQuestion(Question question) {
        System.out.println(String.format("%s. %s \n",question.getId(),question.getWording()));
    }
}
