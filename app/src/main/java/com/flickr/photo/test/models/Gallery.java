package com.flickr.photo.test.models;



import java.util.ArrayList;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * @author Humam Alayad
 *
 * The 'Photo' class represents Gallery of photoes
 */
@Root(name = "rsp", strict = false)
public class Gallery {

    @ElementList(name = "item", inline = true)
    @Path("photos")
    public ArrayList<Photo> photoesList;

    public ArrayList<Photo> getPhotoesList() {
        return photoesList;
    }

    public void setPhotoesList(ArrayList<Photo> photoesList) {
        this.photoesList = photoesList;
    }


}

