package hr.foi.air.crvenkappica.web;

public class WebParams {
    public String service; //prijava.php
    public String hash;
    public String type;
    public String params; //

    public static class WebInfo{
        public static String url="http://www.redtesseract.sexy/crvenkappica/";

        public static String getUrl() {
            return url;
        }
    }
}
