package fr.oncohospital.model.repository;

import java.util.ArrayList;
import java.util.List;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.data.ContactEntity;
import fr.oncohospital.ui.MenuActivity;

/**
 * Created by Assia HACHICHI on 24/03/2021
 */
public class ContactRepository {
    private  List<ContactEntity> allContacts;
    private static ContactRepository instance;

    /**
     *
     * @return
     */
    public static ContactRepository getInstance(){
        if (instance == null){instance = new ContactRepository();}
        instance.init();
        return instance;
    }

    /**
     *
     */
    private ContactRepository() {
        allContacts = new ArrayList<ContactEntity>();
    }

    /**
     *
     */
    private void init(){
        allContacts = new ArrayList<ContactEntity>();
        if(MenuActivity.getLanguage().equals("fr")){
            populateContactFr();
        }else{
            populateContactEn();
            }

    }

    /**
     *
     * @return
     */
    public  List<ContactEntity> getAllContacts() {
        return allContacts;
    }

    /**
     *
     */
    public void populateContactFr(){
        ContactEntity c ;
        c = new ContactEntity("Oncophone Sein-Gynécologie", R.mipmap.ic_oncophone_background,
                "Oncophone Sein-Gynécologie : Pour prendre un RDV ou obtenir des informations médicales, veuillez contacter le numéro de téléphone suivant, du lundi au vendredi, et de 8h30 à 18h00.",
                "+33 12 12 12 12", "0", "ou par courriel à l'adresse suivante",
                "sein@oncohospital.fr");
        allContacts.add(c);
        c = new ContactEntity("Radiothérapie", R.mipmap.ic_radiotherapie_background,
                "Si vous avez des questions ou des problèmes pendant la radiothérapie, les manipulatrices de radiothérapie sont joignables de 08h00 à 18h00 au : ",
                "+33 11 11 11 11", "+33 13 13 13 13",
                "Veuillez contacter en priorité l’équipe de votre machine de traitement. Le numéro de téléphone est présent sur votre carnet de rendez-vous",
                "0");
        allContacts.add(c);
    }

    /**
     *
     */
    public void populateContactEn(){
        ContactEntity c ;
        c = new ContactEntity("Oncophone Breast-Gynecology", R.mipmap.ic_oncophone_background,
                "Oncophone Breast-Gynecology: To make an appointment or obtain medical information, please contact the following telephone number, from Monday to Friday, and from 8:30 am to 6:00 pm.",
                "+33 12 12 12 12 ", "0", "or by email at the following address",
                "sein@oncohospital.fr");
        allContacts.add(c);
        c = new ContactEntity("Radiotherapy", R.mipmap.ic_radiotherapie_background,
                "If you have questions or problems during radiotherapy, the radiotherapy technicians can be contacted from 8 a.m. to 6 p.m. at: ",
                "+33 11 11 11 11", "+33 13 13 13 13",
                "Please contact your processing machine team first. The phone number is on your appointment book.",
                "0");
        allContacts.add(c);
    }
}
