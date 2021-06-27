package anikan.homework.dao;


import anikan.homework.domain.Question;

import java.util.List;


public interface QuestionDao {
    /**
     *
     * @return All questions in repository
     */
    List<Question> getAll();



    /**
     * Gets existing question in repository, instead return null
     * @param id
     * @return
     */
    Question getById(String id);

}
