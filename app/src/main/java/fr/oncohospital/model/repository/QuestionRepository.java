package fr.oncohospital.model.repository;

import java.util.ArrayList;
import java.util.List;

import fr.oncohospital.model.allData.data.QuestionEntity;
import fr.oncohospital.ui.MenuActivity;

/**
 * Created by Assia HACHICHI on 24/03/2021
 */
public class QuestionRepository {
    private static QuestionRepository instance;
    private List<QuestionEntity> allQuestions;

    /**
     *
     * @return
     */
    public static QuestionRepository getInstance(){
        if (instance == null){instance = new QuestionRepository();}
        instance.init();
        return instance;
    }

    /**
     *
     */
    private QuestionRepository(){
        allQuestions = new ArrayList<QuestionEntity>();
    }

    /**
     *
     */
    private void init(){
        allQuestions = new ArrayList<QuestionEntity>();
        if(MenuActivity.getLanguage().equals("fr")){
            populateQuestionFr();
        }else{
            populateQuestionEn();
        }
    }

    /**
     *
     */
    public void populateQuestionEn(){
        allQuestions.add(new QuestionEntity("Nausea, vomiting?", "Take Vogalene ..."));
        allQuestions.add(new QuestionEntity("Watery eyes?", "Moisten with physiological serum ..."));
        allQuestions.add(new QuestionEntity("Lack of appetite?", "Split your meal into many meals per day ..."));
        allQuestions.add(new QuestionEntity("Pain?", "Take paracetamol ..."));
        allQuestions.add(new QuestionEntity("Fever?", "Please do blood tests and contact your doctor ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));

        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));

    }

    /**
     *
     */
    public void populateQuestionFr(){
        allQuestions.add(new QuestionEntity("Naus??es, vomissements ?", "Prenez Vogalene ..."));
        allQuestions.add(new QuestionEntity("Yeux larmoyants ?", "Mettez du s??rum physiologique ..."));
        allQuestions.add(new QuestionEntity("Manque d'app??tit ?", "Fractionner votre repas ..."));
        allQuestions.add(new QuestionEntity("Douleurs ?", "Prenez parac??tamol ..."));
        allQuestions.add(new QuestionEntity("Fi??vre ?", "Faites des analyses sanguines et contacter votre m??decin traitant ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));

        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));
        allQuestions.add(new QuestionEntity("Question ?", "Answer ..."));

    }

    /**
     *
     * @return
     */
    public List<QuestionEntity> getAllQuestions() {
        return allQuestions;
    }
}
