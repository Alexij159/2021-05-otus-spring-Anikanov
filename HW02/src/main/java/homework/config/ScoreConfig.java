package homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "score-properties")
public class ScoreConfig {
    private Map<Integer,String> scores;

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


