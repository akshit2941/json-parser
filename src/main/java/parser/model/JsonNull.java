package parser.model;

public class JsonNull implements JsonValue {

    public static final JsonNull INSTANCE = new JsonNull();

    private JsonNull() {}

    @Override
    public String toString() {
        return "null";
    }
}