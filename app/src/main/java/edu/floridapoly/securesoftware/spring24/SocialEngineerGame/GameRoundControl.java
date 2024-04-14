package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;
public class GameRoundControl {
    private GameContent gameContent;  // Assuming GameContent is already defined
    private int correctAnswersSoFar;

    public GameRoundControl(GameContent gameContent) {
        this.gameContent = gameContent;
        this.correctAnswersSoFar = 0;
    }

    public Question getNextQuestion() {
        // Get the next question from GameContent
        return gameContent.getNewQuestion();
    }

    public String tryAnswer(int answerIndex) {
        Question currentQuestion = getNextQuestion();
        if (currentQuestion != null && currentQuestion.isAnswerCorrect(answerIndex)) {
            correctAnswersSoFar++;
            return currentQuestion.answerFeedback + " Correct!";
        } else if (currentQuestion != null) {
            return currentQuestion.answerFeedback + " Incorrect.";
        } else {
            return "There are no more questions.";
        }
    }

    public PastScore endGame() {
        // Return a summary of the game, with the total number of questions and the number of correct answers
        PastScore score = new PastScore();  // Assuming PastScore is a class with a suitable constructor or setters
        return score;
    }
}
