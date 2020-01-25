package nl.maikel.utils;

public enum Message {

    NOT_FOUND("No recipe found with the provided ID.");

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
