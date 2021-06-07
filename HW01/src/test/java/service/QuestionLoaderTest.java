package service;

import Exceptions.QuestionsNotFoundException;
import dao.QuestionDao;
import dao.QuestionDaoImpl;
import domain.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionLoaderTest {
    @Mock
    private QuestionDaoImpl questionDao;


    @Test
    public void loadQuestionNormalWork() throws IOException {

        QuestionLoader questionLoader = new QuestionLoader("data/questions.csv", questionDao);
        questionLoader.loadQuestions();

        given(questionDao.getAll()).willReturn(List.of(new Question(),new Question(),new Question(),new Question(),new Question()));
        assertThat(questionDao.getAll().size()).isEqualTo(5);
    }

    @Test
    void loadQuestionsShouldThrowQuestionsNotFoundException() {
        QuestionLoader questionLoader = new QuestionLoader("data/NOquestions.csv", questionDao);

        assertThrows(QuestionsNotFoundException.class, () -> questionLoader.loadQuestions());
    }
}

