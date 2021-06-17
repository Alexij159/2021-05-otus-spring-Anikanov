package dao;

import domain.Question;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class QuestionDaoImpl implements QuestionDao{

    private List<Question> questions;

    public QuestionDaoImpl(List<Question> questions) {
        this.questions = questions;
    }


    public QuestionDaoImpl() {
        questions = new LinkedList<>();
    }

    @Override
    public List<Question> getAll() {
        return new LinkedList<>(questions);
    }

    @Override
    public boolean save(Question question) {
        if (Objects.isNull(question) || questions.contains(question)
                || Objects.nonNull(getById(question.getId())) ) {
            return false;
        }

        return questions.add(question);
    }

    @Override
    public Question getById(String id) {
        if (Objects.isNull(id) || id.equals("") )
            return null;
        return questions.stream().filter(question -> Objects.nonNull(question.getId()) && id.equals(question.getId()))
                .findFirst().orElse(null);
    }

    @Override
    public boolean deleteById(String id) {
        if (Objects.isNull(id) || id.equals("") )
            return false;
        int index = questions.indexOf(getById(id));
        if (index == -1) {
            return false;
        }
        questions.remove(index);
        return true;
    }

}
