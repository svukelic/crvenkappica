package hr.foi.air.crvenkappica.login;

public class LoginStatus {

    //staticka klasa s getterima i setterima, pamti se username ulogiranog korisnika, status i id
    //takoder se pamti koji profil se treba ucitati prilikom prikaza

    public static class LoginInfo{

        public static String loginName;
        public static Boolean loginState;
        public static String loginID;
        public static String profilSearch;


        public static String getLoginName() {
            return loginName;
        }

        public static void setLoginName(String loginName) {
            LoginInfo.loginName = loginName;
        }

        public static Boolean getLoginState() {
            return loginState;
        }

        public static void setLoginState(Boolean loginState) {
            LoginInfo.loginState = loginState;
        }

        public static String getLoginID() {
            return loginID;
        }

        public static void setLoginID(String loginID) {
            LoginInfo.loginID = loginID;
        }

        public static String getProfilSearch() {
            return profilSearch;
        }

        public static void setProfilSearch(String profilSearch) {
            LoginInfo.profilSearch = profilSearch;
        }
    }

}
