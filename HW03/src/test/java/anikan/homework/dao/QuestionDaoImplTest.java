package anikan.homework.dao;

import anikan.homework.Exceptions.QuestionsNotFoundException;
import anikan.homework.domain.Question;
import anikan.homework.config.LocaleProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {
    @Mock
    private LocaleProvider localeProvider;
    private static final String QUESTIONS_FILE_PATH = "data/questions_";
    private static final String EMPTY_QUESTIONS_FILE_PATH = "data/emptyQuestions_";

    @Test
    public void daoInitializeTest(){
        given(localeProvider.getLocale()).willReturn(Locale.US);
        QuestionDao questionDao = new QuestionDaoImpl(QUESTIONS_FILE_PATH, localeProvider);
        assertThat(questionDao).isNotNull();
    }


    @Test
    public void getAllReturnEmptyList(){
        given(localeProvider.getLocale()).willReturn(Locale.US);
        QuestionDao questionDao = new QuestionDaoImpl(EMPTY_QUESTIONS_FILE_PATH, localeProvider);
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

        given(localeProvider.getLocale()).willReturn(Locale.US);
        QuestionDao questionDao = new QuestionDaoImpl(QUESTIONS_FILE_PATH, localeProvider);
        assertThat(questionDao.getAll()).usingRecursiveComparison().isEqualTo(questions);
    }


    @Test
    void loadQuestionsShouldThrowQuestionsNotFoundException() {
        given(localeProvider.getLocale()).willReturn(Locale.US);
        assertThrows(QuestionsNotFoundException.class, () -> new QuestionDaoImpl("NonExistQuestions.csv", localeProvider));
    }

    @Test
    void saveNewQuestionCorrectly() {
        given(localeProvider.getLocale()).willReturn(Locale.US);
        QuestionDao questionDao = new QuestionDaoImpl(EMPTY_QUESTIONS_FILE_PATH, localeProvider);
        assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(true);
        assertThat(questionDao.getById("1").getWording()).isEqualTo("WTF?");
    }

    @Test
    void saveRepeatedQuestionReturnFalse() {
        given(localeProvider.getLocale()).willReturn(Locale.US);
        QuestionDao questionDao = new QuestionDaoImpl(QUESTIONS_FILE_PATH, localeProvider);
        //assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(true);
        assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(false);

    }

    @Test
    void getByIdNormalWork() {
        given(localeProvider.getLocale()).willReturn(Locale.US);
        QuestionDao questionDao = new QuestionDaoImpl(EMPTY_QUESTIONS_FILE_PATH, localeProvider);
        questionDao.save(new Question("1", "WTF?","Nothing"));
        assertThat(questionDao.getById("1").getCorrectAnswer()).isEqualTo("Nothing");
    }

    @Test
    void getByIdShouldReturnNull() {
        given(localeProvider.getLocale()).willReturn(Locale.US);
        QuestionDao questionDao = new QuestionDaoImpl(EMPTY_QUESTIONS_FILE_PATH, localeProvider);
        questionDao.save(new Question("1", "WTF?","Nothing"));
        assertThat(questionDao.getById("2")).isNull();
    }

}
