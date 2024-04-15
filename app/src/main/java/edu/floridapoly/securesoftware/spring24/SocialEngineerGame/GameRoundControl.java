package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;
public class GameRoundControl {
    private GameContent gameContent;
    private int correctAnswersSoFar;
    private int questionsAskedSoFar;
    private Question currentQuestion; // Add this line to store the current question

    public GameRoundControl(GameContent gameContent) {
        this.gameContent = gameContent;
        this.correctAnswersSoFar = 0;
        this.questionsAskedSoFar = 0;
        this.currentQuestion = null; // Initialize current question as null
    }

    public Question getNextQuestion() {
        if (questionsAskedSoFar < 5) { // Only allow 5 questions per round
            currentQuestion = gameContent.getNewQuestion(); // Get and store the new question
            questionsAskedSoFar++;
            return currentQuestion;
        } else {
            return null; // No more questions allowed in this round
        }
    }

    public String tryAnswer(int answerIndex) {
        if (currentQuestion == null) {
            return "Please get the question first.";
        }

        String feedback;
        if (currentQuestion.isAnswerCorrect(answerIndex)) {
            correctAnswersSoFar++;
            feedback = "Correct! " + currentQuestion.answerFeedback;
        } else {
            feedback = "Incorrect. " + currentQuestion.answerFeedback;
        }
        // Now it's safe to set currentQuestion to null because we're done using it
        currentQuestion = null;
        return feedback;
    }

    public PastScore endGame() {
        // Return a summary of the game
        PastScore score = new PastScore();
        // Set the necessary properties of score based on the game state
        gameContent.resetRound(); // Prepare for a new round if needed
        return score;
    }
}
