package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameContent {
    private List<Question> questions;
    private Random random; // Random object to select a random question
    private List<Question> askedQuestions;

    // Load a random question from JSON file
    public GameContent(List<Question> questions) {
        this.questions = questions; // Keep original list
        this.askedQuestions = new ArrayList<>();
        random = new Random();
        shuffleQuestions();
    }


    public Question getNewQuestion() {
        if (questions.isEmpty()) {
            return null;
        }
        Question question = questions.remove(0); // Always take the first question after shuffling
        askedQuestions.add(question); // Keep track of asked questions
        return question;
    }


    // Function to shuffle the questions
    private void shuffleQuestions() {
        Collections.shuffle(questions, random);
    }

    // Reset the game content for new round
    public void resetRound() {
        questions.addAll(askedQuestions);
        askedQuestions.clear();
        shuffleQuestions();
    }
}