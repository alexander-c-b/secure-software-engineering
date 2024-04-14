package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameContent {
    private List<Question> questions;
    private Random random; // Random object to select a random question


    // Load a random question from JSON file
    public GameContent(List<Question> questions) {
        this.questions = questions;
        random = new Random();
        shuffleQuestions();
    }


    public Question getNewQuestion() {
        if (questions.isEmpty()) {
            return null; // Return null or throw an exception if there are no questions
        }
        return questions.get(random.nextInt(questions.size()));
    }


    // Function to shuffle the questions
    private void shuffleQuestions() {
        Collections.shuffle(questions, random);
    }
}