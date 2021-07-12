package anikan.homework.service;

import anikan.homework.dao.QuestionDao;
import anikan.homework.domain.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> getQuestions() {
        return dao.getAll();
    }

}
