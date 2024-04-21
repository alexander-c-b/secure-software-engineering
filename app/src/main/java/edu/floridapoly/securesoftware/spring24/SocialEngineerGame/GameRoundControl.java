package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

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
        if (questionsAskedSoFar >= 5) { // Game should end after 5 questions
            return null;
        }
        currentQuestion = gameContent.getNewQuestion(); // Get and store the new question
        questionsAskedSoFar++;
        return currentQuestion;
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


    public void endGame() {
        // Return a summary of the game
        PastScore score = new PastScore(5, correctAnswersSoFar);

        // Call savePastScores() here to save the score
        try {
            JsonEncoder jsonEncoder = new JsonEncoder(App.getContext()); // App.getContext() needs to be your actual context getter method
            jsonEncoder.savePastScore(
              score, User.getUsername(), User.getPasswordHash(),
              User.getPasswordHashSalted()
            );
            Log.d("Game", "Final Score: " + score.correctAnswers + "/" + score.totalQuestions);

        } catch (IOException e) {
            // Handle any IO Exceptions here
            e.printStackTrace();
        }
        Toast.makeText(App.getContext(), "You got " + correctAnswersSoFar + " out of 5 questions correct.", Toast.LENGTH_SHORT).show();
        // Reset the game round control for a new game
        gameContent.resetRound();
        correctAnswersSoFar = 0;
        questionsAskedSoFar = 0;
        currentQuestion = null;

        // Navigate back to the previous screen
        Intent intent = new Intent(App.getContext(), Game.class); // Game.class should be the previous screen
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(intent);
    }

}
