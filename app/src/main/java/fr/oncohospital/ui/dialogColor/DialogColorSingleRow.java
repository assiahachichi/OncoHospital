package fr.oncohospital.ui.dialogColor;

/**
 * Created by Assia HACHICHI on 18/03/2021
 */
public class DialogColorSingleRow {
    private final String nameLanguage;
    private String name;
    private int imageFull;
    private int imageNotFull;
    private int color;


    public DialogColorSingleRow(String name, int imageFull, int imageNotFull, int color, String nameLanguage) {
        this.name = name;
        this.imageFull = imageFull;
        this.imageNotFull = imageNotFull;
        this.color = color;
        this.nameLanguage = nameLanguage;
    }
    public String getName() {
        return name;
    }
    public String getNameLanguage() {
        return nameLanguage;
    }

    public int getColor() {
        return color;
    }

    public int getImageFull() {
        return imageFull;
    }

    public int getImageNotFull() {
        return imageNotFull;
    }

    @Override
    public String toString() {
        return "SingleRow{" +
                "name='" + name + '\'' +
                ", imageFull=" + imageFull +
                ", imageNotFull=" + imageNotFull +
                ", color=" + color +
                '}';
    }
}
