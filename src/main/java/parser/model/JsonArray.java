package parser.model;

import java.util.ArrayList;
import java.util.List;

public class JsonArray implements JsonValue{
    private final List<JsonValue> values = new ArrayList<>();

    public void add(JsonValue value){
        values.add(value);
    }

    public JsonValue get(int index){
        return values.get(index);
    }

    public List<JsonValue> getAll(){
        return values;
    }

    @Override
    public String toString(){
        return values.toString();
    }
}
