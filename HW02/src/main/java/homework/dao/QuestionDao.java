package homework.dao;


import com.anikan.homework.domain.Question;

import java.util.List;


public interface QuestionDao {
    /**
     *
     * @return All questions in repository
     */
    List<Question> getAll();

    /**
     * Saves question in repo if there is no one and return true, instead return false.
     * @param question
     * @return
     *
     */
    boolean save(Question question);

    /**
     * Gets existing question in repository, instead return null
     * @param id
     * @return
     */
    Question getById(String id);

}
