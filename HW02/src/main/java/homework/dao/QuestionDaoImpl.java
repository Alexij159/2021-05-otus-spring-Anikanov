package homework.dao;

import homework.Exceptions.LoadWentWrongException;
import homework.Exceptions.QuestionsNotFoundException;
import homework.domain.Question;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@PropertySources( {@PropertySource("classpath:scores.properties"),
        @PropertySource("classpath:application.properties")})
public class QuestionDaoImpl implements QuestionDao{


    private final List<Question> questions;

    private String questionsFilePath;


    public QuestionDaoImpl( @Value("${questions.filePath}") String questionsFilePath) {
        this.questionsFilePath = questionsFilePath;
        questions = new LinkedList<>();
        loadQuestions();
    }

    @Override
    public List<Question> getAll() {
        return new LinkedList<>(questions);
    }

    @Override
    public boolean save(Question question) {
        if (isNull(question) || questions.contains(question)
                || nonNull(getById(question.getId())) ) {
            return false;
        }

        return questions.add(question);
    }


    @Override
    public Question getById(String id) {
        if ("".equals(id) )
            return null;
        return questions.stream().filter(question -> nonNull(question.getId()) && id.equals(question.getId()))
                .findFirst().orElse(null);
    }


    private void loadQuestions() {
        File questionsFile = null;
        try {
            questionsFile = new ClassPathResource(questionsFilePath).getFile();
        } catch (IOException e) {
            throw new QuestionsNotFoundException("Файл с вопросами отсутствует!");
        }
        CsvToBean<Question> csv = new CsvToBean<>();
        csv.setMappingStrategy(setColumnMapping());
        try (CSVReader reader = new CSVReader(new FileReader(questionsFile))){
            csv.setCsvReader(reader);
            List<Question> questions = csv.parse();
            questions.forEach(this::save);
        } catch (FileNotFoundException e) {
            throw new QuestionsNotFoundException(e);
        } catch (IOException e) {
            throw new LoadWentWrongException(e);
        }
    }

    public void setQuestionsFilePath(String questionsFilePath) {
        this.questionsFilePath = questionsFilePath;
    }

    private ColumnPositionMappingStrategy<Question> setColumnMapping()
    {
        ColumnPositionMappingStrategy<Question> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Question.class);
        String[] columns = new String[] {"id", "wording", "correctAnswer"};
        strategy.setColumnMapping(columns);
        return strategy;
    }

}
