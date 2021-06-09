package homework.dao;

import com.anikan.homework.Exceptions.QuestionsNotFoundException;
import com.anikan.homework.domain.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {

    private static final String QUESTIONS_FILE_PATH = "data/questions.csv";
    private static final String EMPTY_QUESTIONS_FILE_PATH = "data/emptyQuestions.csv";
    @Test
    public void daoInitializeTest(){
        QuestionDao questionDao = new QuestionDaoImpl(QUESTIONS_FILE_PATH);
        assertThat(questionDao).isNotNull();
    }


    @Test
    public void getAllReturnEmptyList(){
        QuestionDao questionDao = new QuestionDaoImpl(EMPTY_QUESTIONS_FILE_PATH);
        assertThat(questionDao.getAll()).isEmpty();
    }

    @Test
    public void getAllReturnAllTest() {
        List<Question> questions = new LinkedList<>();
        questions.add(new Question("1", "WTF?","Nothing"));
        questions.add(new Question("2", "Is London capital of GB?","Yes"));
        questions.add(new Question("3","Who is on the first base?","Who"));
        questions.add(new Question("4","What is the capital of Great Britain?","London"));
        questions.add(new Question("5","Why so serious?","Batman"));

        QuestionDao myDao = new QuestionDaoImpl(QUESTIONS_FILE_PATH);
        assertThat(myDao.getAll()).usingRecursiveComparison().isEqualTo(questions);
    }


    @Test
    void loadQuestionsShouldThrowQuestionsNotFoundException() {
        assertThrows(QuestionsNotFoundException.class, () -> new QuestionDaoImpl("NonExistQuestions.csv"));
    }

    @Test
    void saveNewQuestionCorrectly() {
        QuestionDao questionDao = new QuestionDaoImpl(EMPTY_QUESTIONS_FILE_PATH);
        assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(true);
        assertThat(questionDao.getById("1").getWording()).isEqualTo("WTF?");
    }

    @Test
    void saveRepeatedQuestionReturnFalse() {
        QuestionDao questionDao = new QuestionDaoImpl(QUESTIONS_FILE_PATH);
        //assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(true);
        assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(false);

    }

    @Test
    void getByIdNormalWork() {
        QuestionDao questionDao = new QuestionDaoImpl(EMPTY_QUESTIONS_FILE_PATH);
        questionDao.save(new Question("1", "WTF?","Nothing"));
        assertThat(questionDao.getById("1").getCorrectAnswer()).isEqualTo("Nothing");
    }

    @Test
    void getByIdShouldReturnNull() {
        QuestionDao questionDao = new QuestionDaoImpl(EMPTY_QUESTIONS_FILE_PATH);
        questionDao.save(new Question("1", "WTF?","Nothing"));
        assertThat(questionDao.getById("2")).isNull();
    }

}
