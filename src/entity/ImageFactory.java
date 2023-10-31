package entity;

public class ImageFactory {

    public Image create(String caption) {
        return new Image(caption);
    }
}
