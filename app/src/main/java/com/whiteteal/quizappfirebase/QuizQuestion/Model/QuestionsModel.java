package com.whiteteal.quizappfirebase.QuizQuestion.Model;

import java.util.ArrayList;

public class QuestionsModel {
    String Question;
    String Answer;
    String Explanation;
    Integer QuestionType;
    String media_question;
    ArrayList<String> options = new ArrayList<>();

    public String getMedia_question() {
        return media_question;
    }

    public void setMedia_question(String media_question) {
        this.media_question = media_question;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getExplanation() {
        return Explanation;
    }

    public void setExplanation(String explanation) {
        Explanation = explanation;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
    public Integer getQuestionType() {
        return QuestionType;
    }

    public void setQuestionType(Integer questionType) {
        QuestionType = questionType;
    }

}
