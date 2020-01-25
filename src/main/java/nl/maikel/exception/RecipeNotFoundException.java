package nl.maikel.exception;

public class RecipeNotFoundException extends Exception {
    public RecipeNotFoundException() {
    }

    public RecipeNotFoundException(String msg) {
        super(msg);
    }
}
