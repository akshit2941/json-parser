package parser.model;

import java.util.HashMap;
import java.util.Map;

public class JsonObject implements JsonValue{
    private final Map<String, JsonValue> values = new HashMap<>();

    public void put(String key, JsonValue value){
        values.put(key, value);
    }

    public JsonValue get(String key){
        return values.get(key);
    }

    public Map<String, JsonValue> getAll(){
        return values;
    }

    @Override
    public String toString(){
        return values.toString();
    }
}
