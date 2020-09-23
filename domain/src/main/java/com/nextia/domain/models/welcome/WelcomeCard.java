package com.nextia.domain.models.welcome;

import javax.swing.text.html.ImageView;

public class WelcomeCard {
    String title;
    String description;
    int imagen;
    public WelcomeCard(int imagen, String title, String description) {
        this.title = title;
        this.description = description;
        this.imagen = imagen;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImagen() {
        return imagen;
    }


}
