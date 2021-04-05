package fr.oncohospital.model.allData.remoteDataSource;


/**
 * Created by Assia HACHICHI on 10/02/2021
 */
public class ServerUrls {
        /**
        * IP_SERVER_ADDRESS represents IP address of the hospital server
        */
        private static String IP_SERVER_ADDRESS = "192.168.1.51";

        /**
        * NAME_ONCO_HSOPITAL represents the name of hospital
        */
        private static String NAME_ONCO_HSOPITAL = "oncopole";

        /**
        * represents the server URL to log in
        */
        public static final String URL_LOGIN_PATIENT = "http://" + IP_SERVER_ADDRESS
                + "/"+NAME_ONCO_HSOPITAL+"/functions.php?function=login";
        /**
        * represents the server URL to get appointment historic
        */
        public static final String URL_APPOINTMENT_HISTORIC = "http://" + IP_SERVER_ADDRESS
                + "/"+NAME_ONCO_HSOPITAL+"/functionsGETHistorique.php?";
}
