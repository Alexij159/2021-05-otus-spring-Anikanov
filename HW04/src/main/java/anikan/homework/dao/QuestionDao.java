package anikan.homework.dao;


import anikan.homework.domain.Question;

import java.util.List;


public interface QuestionDao {
    /**
     *
     * @return All questions in repository
     */
    List<Question> getAll();


}
