package service;

import Exceptions.QuestionsNotFoundException;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import domain.Question;
import dao.QuestionDao;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class QuestionLoader {

    private final String questionsFilePath;

    private final QuestionDao dao;

    public QuestionLoader(String questionsFilePath, QuestionDao dao) {
        this.questionsFilePath = questionsFilePath;
        this.dao = dao;
    }

    public void loadQuestions() throws IOException {
        File questionsFile = null;
        try {
            questionsFile = new ClassPathResource(questionsFilePath).getFile();
            if (!questionsFile.exists()){
                throw new QuestionsNotFoundException();
            }
        } catch (FileNotFoundException e){
            throw new QuestionsNotFoundException(e);
        }

        CsvToBean<Question> csv = new CsvToBean<>();
        csv.setMappingStrategy(setColumnMapping());
        csv.setCsvReader(new CSVReader(new FileReader(questionsFile)));
        List<Question> questions = csv.parse();
        for (Question q : questions) {
            dao.save(q);
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

    public String getQuestionsFilePath() {
        return questionsFilePath;
    }

}
