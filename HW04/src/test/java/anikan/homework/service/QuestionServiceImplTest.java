package anikan.homework.service;

import anikan.homework.dao.QuestionDao;
import anikan.homework.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class QuestionServiceImplTest {
    @MockBean
    private QuestionDao questionDao;

    @Autowired
    private QuestionServiceImpl questionService;

    @Test
    public void getQuestionNormalWork(){
        Question q = new Question("1","WTF?","Nothing");

        given(questionDao.getAll()).willReturn(Collections.singletonList(q));
        assertThat(questionService.getQuestions()).usingRecursiveComparison().isEqualTo(Collections.singletonList(q));
    }

    @Configuration
    @Import(QuestionServiceImpl.class)
    static class TestConfiguration{}

}

