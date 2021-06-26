package anikan.homework.dao;

import anikan.homework.Exceptions.QuestionsNotFoundException;
import anikan.homework.config.QuestionsFileNameProvider;
import anikan.homework.domain.Question;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Repository
public class QuestionDaoCsv implements QuestionDao {


    private List<Question> questions;
    private final String questionsFilePath;

    public QuestionDaoCsv(QuestionsFileNameProvider questionsFileNameProvider) {
        this.questionsFilePath = questionsFileNameProvider.getQuestionsFileName();
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
        try {
            csv.setCsvReader(new CSVReader(new FileReader(questionsFile)));
        } catch (FileNotFoundException e) {
            throw new QuestionsNotFoundException(e);
        }
        List<Question> questions = csv.parse();
        for (Question q : questions) {
            save(q);
        }
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
