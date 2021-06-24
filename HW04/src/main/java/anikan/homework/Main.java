package anikan.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);

//        QuestionService questionService = context.getBean(QuestionService.class);
//        questionService.loadQuestions();
//        questionService.showQuestions();



    }
}
