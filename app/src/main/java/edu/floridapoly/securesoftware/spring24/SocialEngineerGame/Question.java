package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import java.util.List;

public class Question {
    public String questionText;
    public List<String> answers;
    public int correctAnswerIndex;
    public String answerFeedback;

    public Question(String questionText, List<String> answers, int correctAnswerIndex, String answerFeedback) {
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
        this.answerFeedback = answerFeedback;
    }

    // Return true if the index is the correct answer
    public boolean isAnswerCorrect(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }

    // Return correct answer string
    public String getCorrectAnswer() {
        if (answers != null && !answers.isEmpty() && correctAnswerIndex >= 0 && correctAnswerIndex < answers.size()) {
            return answers.get(correctAnswerIndex);
        }
        return "Invalid correct answer index";
    }
}
