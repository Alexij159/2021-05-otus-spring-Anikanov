package homework.service;

import homework.dao.QuestionDao;
import homework.domain.Question;
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
