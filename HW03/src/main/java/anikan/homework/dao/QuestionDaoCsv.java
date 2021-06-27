package anikan.homework.dao;

import anikan.homework.Exceptions.QuestionsNotFoundException;
import anikan.homework.config.LocaleProvider;
import anikan.homework.domain.Question;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Repository
public class QuestionDaoCsv implements QuestionDao {


    private final List<Question> questions;
    private final String questionsFilePath;



    public QuestionDaoCsv(@Value("${questions.filePath}") String questionsFilePath, LocaleProvider localeProvider) {
        this.questionsFilePath = questionsFilePath + localeProvider.getLocale() + ".csv";
        questions = new LinkedList<>();
        loadQuestions();
    }

    @Override
    public List<Question> getAll() {
        return new LinkedList<>(questions);
    }


    @Override
    public Question getById(String id) {
        if ("".equals(id) )
            return null;
        return questions.stream().filter(question -> nonNull(question.getId()) && id.equals(question.getId()))
                .findFirst().orElse(null);
    }


    private void loadQuestions() {
        try (InputStream questionsStream = this.getClass().getClassLoader().getResourceAsStream(questionsFilePath)){
            if (isNull(questionsStream))
                throw new QuestionsNotFoundException("Файл с вопросами отсутствует!");
            Reader questionsReader = new InputStreamReader(questionsStream);

            CsvToBean<Question> csv = new CsvToBean<>();
            csv.setMappingStrategy(setColumnMapping());
            csv.setCsvReader(new CSVReader(questionsReader));
            List<Question> questionsToSave = csv.parse();
            questions.addAll(questionsToSave);

        } catch (FileNotFoundException e) {
            throw new QuestionsNotFoundException(e);
        } catch (IOException e) {
            throw new QuestionsNotFoundException("Файл с вопросами отсутствует!");
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
