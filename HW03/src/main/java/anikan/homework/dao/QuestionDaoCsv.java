package anikan.homework.dao;

import anikan.homework.Exceptions.QuestionsNotFoundException;
import anikan.homework.domain.Question;
import anikan.homework.service.FileNameProvider;
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
    private final String questionsFilePath;

    public QuestionDaoCsv(FileNameProvider fileNameProvider) {
        questionsFilePath = fileNameProvider.getQuestionsFilePath();
        loadQuestions();
    }

    @Override
    public List<Question> getAll() {
        return loadQuestions();
    }


    private List<Question> loadQuestions() {
        try (InputStream questionsStream = this.getClass().getClassLoader().getResourceAsStream(questionsFilePath)){
            if (isNull(questionsStream))
                throw new QuestionsNotFoundException("Файл с вопросами отсутствует!");
            Reader questionsReader = new InputStreamReader(questionsStream);

            CsvToBean<Question> csv = new CsvToBean<>();
            csv.setMappingStrategy(setColumnMapping());
            csv.setCsvReader(new CSVReader(questionsReader));
            List<Question> questionsToSave = csv.parse();
            return questionsToSave;

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
