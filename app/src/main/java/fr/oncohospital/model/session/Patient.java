package fr.oncohospital.model.session;

/**
 * Created by Assia HACHICHI on 10/02/2021
 */

/**
 * This class represents patient, which follows chemotherapy.
 */
public class Patient {

    private String idPatient, name, firstname, email, tel;

    /**
     * This method is the unique constructor of the class Patient
     * @param idPatient is the patient identifier
     * @param name is the patient name
     * @param firstname is the patient firstname
     * @param email is the patient email address
     * @param tel is the patient phone number
     */
    public Patient(String idPatient, String name, String firstname, String email, String tel) {
        this.idPatient = idPatient;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.tel = tel;
    }

    /**
     * This method is a getter, which return a patient identifier.
     * @return return a patient identifier.
     */
    public String getIdPatient() {
        return idPatient;
    }

    /**
     * This method is a getter, which return a patient name.
     * @return return a patient name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method is a getter, which return a patient first name.
     * @return return a patient first name.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * This method is a getter, which return a patient email address.
     * @return return a patient email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method is a getter, which return a patient phone number.
     * @return return a patient phone number.
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method gives a string, which contains values of each attribute.
     * @return a string which contains attribute values
     */
    @Override
    public String toString() {
        return "Patient{" +
                "idPatient='" + idPatient + '\'' +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
