package net.natura.naturafirebase.notifications.model;

/**
 * Created by marcos on 25/06/17.
 */

public class NotificationDataModel {
    private String title;
    private String message;
    private String usuario;
    private String photoUrl;

    public NotificationDataModel(){}

    public NotificationDataModel(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
