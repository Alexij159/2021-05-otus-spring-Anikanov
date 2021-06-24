package homework.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component

public class ScoreConfig {

    private Map<Integer,String> scores;

    public ScoreConfig(@Value("#{${score}}") Map<Integer, String> scores) {
        this.scores = scores;
    }

    public Map<Integer, String> getScores() {
        return scores;
    }

    public void setScores(Map<Integer, String> scores) {
        this.scores = scores;
    }

    public String getDescriptionFor(int score) {
        return scores.getOrDefault(score,"good Mark =)");
    }
}


