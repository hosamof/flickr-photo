package com.flickr.photo.test.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * @author Humam Alayad
 *
 * The 'Photo' class represents one single photo
 */
@Root(name = "photo", strict = false)
public class Photo {

    @Attribute(name = "id")
    private String id;

    @Attribute(name = "owner")
    private String owner;

    @Attribute(name = "secret")
    private String secret;

    @Attribute(name = "server")
    private String server;

    @Attribute(name = "farm")
    private String farm;

    @Attribute(name = "title")
    private String title;

    @Attribute(name = "ispublic")
    private String ispublic;

    @Attribute(name = "isfriend")
    private String isfriend;

    @Attribute(name = "isfamily")
    private String isfamily;

    public String getUrl(){
        return "https://farm"
        + this.farm // {farm-id}
        + ".staticflickr.com/"
                + this.server // {server-id}
        + "/"
                + this.id // {photo-id}
        + "_"
                + this.secret
        + ".jpg"; // file extension
    }

}