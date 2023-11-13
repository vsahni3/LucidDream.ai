package entity;

public class ImageFactory {

    public Image create(byte[] contents) {
        return new Image(contents);
    }
}
