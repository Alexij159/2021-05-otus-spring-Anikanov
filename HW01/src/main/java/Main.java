

import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.QuestionService;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");


        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.loadQuestions();
        questionService.showQuestions();



    }
}
