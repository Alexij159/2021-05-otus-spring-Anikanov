package anikan.homework.dao;

import anikan.homework.Exceptions.QuestionsNotFoundException;
import anikan.homework.config.LocaleProvider;
import anikan.homework.domain.Question;
import anikan.homework.service.FileNameProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
    void getAllShouldThrowQuestionsNotFoundException() {
        QuestionDaoCsv questionDaoCsv = new QuestionDaoCsv(new FileNameProvider("NonExistQuestions.csv", new LocaleProvider(null)));
        assertThrows(QuestionsNotFoundException.class, questionDaoCsv::getAll);
    }



    @SpringBootConfiguration
    @Import(LocaleProvider.class)
    static class TestConfiguration {

        @Bean("fullQuestionProvider")
        FileNameProvider getFullQuestionProvider(@Value("${questions.filePath.full}") String questionsFilePath, LocaleProvider localeProvider){
            return new FileNameProvider(questionsFilePath, localeProvider);
        }

        @Bean("emptyQuestionProvider")
        FileNameProvider getEmptyQuestionProvider(@Value("${questions.filePath.empty}") String questionsFilePath, LocaleProvider localeProvider){
            return new FileNameProvider(questionsFilePath, localeProvider);
        }

        @Bean("fullQuestionDao")
        QuestionDao getFullQuestionDao(@Qualifier("fullQuestionProvider") FileNameProvider fileNameProvider){
            return new QuestionDaoCsv(fileNameProvider);
        }
        

        @Bean("emptyQuestionDao")
        QuestionDao getEmptyQuestionDao(@Qualifier("emptyQuestionProvider") FileNameProvider fileNameProvider){
            return new QuestionDaoCsv(fileNameProvider);
        }
    }
}
