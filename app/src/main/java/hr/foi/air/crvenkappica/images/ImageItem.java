package hr.foi.air.crvenkappica.images;

import android.graphics.Bitmap;

/**
 * Created by Mario on 20/12/2015.
 */
//Klasa u za slike koje se spremaju sa web adresa te prikazuju u gridview-u.
public class ImageItem {
    private Bitmap image;
    private String title;
    private String id;
    public ImageItem(Bitmap image) {
        super();
        this.image = image;
    }
    public  ImageItem(){
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
