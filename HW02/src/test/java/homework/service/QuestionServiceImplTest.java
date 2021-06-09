package homework.service;

import com.anikan.homework.dao.QuestionDao;
import com.anikan.homework.domain.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    public void getQuestionNormalWork(){
        Question q = new Question("1","WTF?","Nothing");

        given(questionDao.getAll()).willReturn(Collections.singletonList(q));
        assertThat(questionService.getQuestions()).usingRecursiveComparison().isEqualTo(Collections.singletonList(q));
    }

}

