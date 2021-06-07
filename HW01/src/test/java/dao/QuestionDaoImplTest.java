package dao;
import domain.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {

    @Test
    public void daoInitializeTest(){
        QuestionDao questionDao = new QuestionDaoImpl();
        assertThat(questionDao).isNotNull();
    }


    @Test
    public void getAllReturnEmptyList(){
        QuestionDao questionDao = new QuestionDaoImpl();
        assertThat(questionDao.getAll()).isEmpty();
    }

    @Test
    public void getAllReturnAllTest() {
        List<Question> questions = new LinkedList<>();
        questions.add(new Question("1", "WTF?","Nothing"));
        questions.add(new Question("2", "Is London capital of GB?","Yes"));
        QuestionDao myDao = new QuestionDaoImpl(questions);
        assertThat(myDao.getAll()).containsAll(questions);
    }


    @Test
    void saveNewQuestionCorrectly() {
        QuestionDao questionDao = new QuestionDaoImpl();
        assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(true);
        assertThat(questionDao.getById("1").getWording()).isEqualTo("WTF?");
    }

    @Test
    void saveRepeatedQuestionReturnFalse() {
        QuestionDao questionDao = new QuestionDaoImpl();
        assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(true);
        assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(false);

    }

    @Test
    void getByIdNormalWork() {
        QuestionDao questionDao = new QuestionDaoImpl();
        questionDao.save(new Question("1", "WTF?","Nothing"));
        assertThat(questionDao.getById("1").getCorrectAnswer()).isEqualTo("Nothing");
    }

    @Test
    void getByIdShouldReturnNull() {
        QuestionDao questionDao = new QuestionDaoImpl();
        questionDao.save(new Question("1", "WTF?","Nothing"));
        assertThat(questionDao.getById("2")).isNull();
    }



    @Test
    void deleteByIdNormalWork() {
        QuestionDao questionDao = new QuestionDaoImpl();
        questionDao.save(new Question("1", "WTF?","Nothing"));
        assertThat(questionDao.deleteById("1")).isEqualTo(true);
        assertThat(questionDao.deleteById("1")).isEqualTo(false);
    }

}
