package anikan.homework.dao;

import anikan.homework.Exceptions.QuestionsNotFoundException;
import anikan.homework.config.LocaleProvider;
import anikan.homework.config.QuestionsFileNameProvider;
import anikan.homework.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class QuestionDaoCsvTest {

    private static final String QUESTIONS_FILE_PATH = "data/questions_";
    private static final String EMPTY_QUESTIONS_FILE_PATH = "data/emptyQuestions_";

    @Autowired
    @Qualifier("fullQuestionDao")
    QuestionDao fullQuestionDao;

    @Autowired
    @Qualifier("emptyQuestionDao")
    QuestionDao emptyQuestionDao;

    @Test
    public void daoInitializeTest(){

        assertThat(fullQuestionDao).isNotNull();
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getAllReturnEmptyList(){
        assertThat(emptyQuestionDao.getAll()).isEmpty();
    }

    @Test
    public void getAllReturnAllTest() {
        List<Question> questions = new LinkedList<>();
        questions.add(new Question("1", "WTF?","Nothing"));
        questions.add(new Question("2", "Is London capital of GB?","Yes"));
        questions.add(new Question("3","Who is on the first base?","Who"));
        questions.add(new Question("4","What is the capital of Great Britain?","London"));
        questions.add(new Question("5","Why so serious?","Batman"));

        assertThat(fullQuestionDao.getAll()).usingRecursiveComparison().isEqualTo(questions);
    }


    @Test
    void loadQuestionsShouldThrowQuestionsNotFoundException() {
        assertThrows(QuestionsNotFoundException.class, () -> new QuestionDaoCsv(new QuestionsFileNameProvider("NonExistQuestions.csv", new LocaleProvider(null))));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void saveNewQuestionCorrectly() {
        assertThat(emptyQuestionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(true);
        assertThat(emptyQuestionDao.getById("1").getWording()).isEqualTo("WTF?");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void saveRepeatedQuestionReturnFalse() {
        //assertThat(questionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(true);
        assertThat(fullQuestionDao.save(new Question("1", "WTF?","Nothing"))).isEqualTo(false);

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getByIdNormalWork() {
        emptyQuestionDao.save(new Question("1", "WTF?","Nothing"));
        assertThat(emptyQuestionDao.getById("1").getCorrectAnswer()).isEqualTo("Nothing");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getByIdShouldReturnNull() {
        emptyQuestionDao.save(new Question("1", "WTF?","Nothing"));
        assertThat(emptyQuestionDao.getById("2")).isNull();
    }

    @SpringBootConfiguration
    @Import(LocaleProvider.class)
    static class TestConfiguration {
        @Bean("fullQuestionProvider")
        QuestionsFileNameProvider getFullQuestionProvider(@Value("${questions.filePath.full}") String questionsFilePath, LocaleProvider localeProvider){
            return new QuestionsFileNameProvider(questionsFilePath, localeProvider);
        }

        @Bean("emptyQuestionProvider")
        QuestionsFileNameProvider getEmptyQuestionProvider(@Value("${questions.filePath.empty}") String questionsFilePath, LocaleProvider localeProvider){
            return new QuestionsFileNameProvider(questionsFilePath, localeProvider);
        }

        @Bean("fullQuestionDao")
        QuestionDao getFullQuestionDao(@Qualifier("fullQuestionProvider") QuestionsFileNameProvider questionsFileNameProvider){
            return new QuestionDaoCsv(questionsFileNameProvider);
        }
        

        @Bean("emptyQuestionDao")
        QuestionDao getEmptyQuestionDao(@Qualifier("emptyQuestionProvider") QuestionsFileNameProvider questionsFileNameProvider){
            return new QuestionDaoCsv(questionsFileNameProvider);
        }
    }
}
