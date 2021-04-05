package fr.oncohospital.model.allData.data;

/**
 * Created by Assia HACHICHI on 24/03/2021
 * @author Assia HACHICHI
 */

/**
 * This class represents an important contact in this hospital
 */
public  class ContactEntity{

    private String title;
    private int image;
    private String text1;
    private String tel1;
    private String tel2;
    private String text2;
    private String email;

    /**
     * This method is a constructor of an important contact
     * @param title represents a contact title
     * @param image represents a contact image resource
     * @param text1 represents a first text to display
     * @param tel1 represents a first number phone
     * @param tel2 represents a second number phone
     * @param text2 represents a second text to display
     * @param email represents an email address
     */
    public ContactEntity( String title, int image, String text1, String tel1, String tel2, String text2, String email) {
        this.title = title;
        this.image = image;
        this.text1 = text1;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.text2 = text2;
        this.email = email;
    }

    /**
     * This method is a getter, which returns a title of this contact.
     * @return represents a contact title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method is a getter, it returns a resource which represents a contact image.
     * @return represents a contact image resource.
     */
    public int getImage() {
        return image;
    }

    /**
     * This method is a getter, which returns a first text to display to patients.
     * @return represents a first text to display.
     */
    public String getText1() {
        return text1;
    }

    /**
     * This method is a getter, which returns a first number phone of this contact
     * @return represents a first number phone.
     */
    public String getTel1() {
        return tel1;
    }

    /**
     * This method is a getter, which returns a second number phone of this contact
     * @return represents a second number phone.
     */
    public String getTel2() {
        return tel2;
    }

    /**
     * This method is a getter, which returns a second text to display to patients.
     * @return represents a second text to display.
     */
    public String getText2() {
        return text2;
    }

    /**
     * This method is a getter, which returns an email address of this contact
     * @return represents an email address.
     */
    public String getEmail() {
        return email;
    }


}
