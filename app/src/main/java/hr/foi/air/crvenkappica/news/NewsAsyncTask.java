package hr.foi.air.crvenkappica.news;

import android.content.Context;
import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import hr.foi.air.crvenkappica.OnTaskCompleted;

/**
 * Created by domagoj on 04.12.15..
 */

/**
 * Klasa za rad s asyntaskom. Služi za dohvat podataka pomoću Jsoup-a.
 */
public class NewsAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

    private String cssQueryText = "div.post-content p";
    private String cssQueryImage = "img[class=post-image]";
    private String cssQueryLinks = "div[class=post-content] a";

    private OnTaskCompleted listener;
    private Context context;
    private static final String url = "http://www.hls.com.hr/";

    public NewsAsyncTask(Context context, OnTaskCompleted listener) {
        this.listener = listener;
        this.context = context;
    }

    /**
     * Metoda pozvana na background dretvi. U ovom slučaju ništa ne prima kao parametre.
     * Nakon što su podaci dohvaćeni, vraća listu stringova.
     */
    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        ArrayList<String> list = new ArrayList<String>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(cssQueryText);

            for (Element el : elements) {
                if (el != null && el.text() != null) {
                    list.add(el.text());
                }
            }
            Elements image = doc.select(cssQueryImage);

            for (Element elem : image) {
                if (elem != null && elem.absUrl("src") != null) {
                    list.add(elem.absUrl("src"));
                }
            }
            Elements links = doc.select("div[class=post-content] a");

            for (Element link : links){
                if (link != null && link.attr("abs:href") != null){
                    list.add(link.attr("abs:href"));
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * Pozvana na UI dretvi nakon što su podaci dohvaćeni.
     * Argument metode je povratni tip podataka metode doInBackground.
     * Poziva se metoda onTaskCompleted iz interface-a.
     */
    @Override
    protected void onPostExecute(ArrayList<String> list) {
        listener.onTaskCompleted(list);
    }

}
