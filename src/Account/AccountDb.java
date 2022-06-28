package Account;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AccountDb {
    
    private String containFiles;
    private String KeyUsername = "Username";
    private String KeyPassword = "Password";
    private String KeyRoles = "Roles";
    private JsonArray tempArray;

    public String getKeyUsername() {
        return KeyUsername;
    }

    public String getKeyPassword() {
        return KeyPassword;
    }

    public String getKeyRoles() {{
        return KeyRoles;
    }}

    public AccountDb(String containFiles) {
        this.containFiles = containFiles;
        tempArray = readAll();
    }

    public JsonObject read(JsonArray jsonArray, int index) {
        JsonObject object = null;

        object = jsonArray.get(index).getAsJsonObject();
        return object;
    }

    public JsonArray readAll() {
        JsonArray array = null;

        try (FileReader reader = new FileReader(containFiles)) {
            array = (JsonArray) JsonParser.parseReader(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public void write() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(containFiles)) {
            gson.toJson(tempArray, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public JsonArray getAll() {
        return tempArray;
    }

    public int search(String Key, String Value) {
        int idx = -1;
        String tempMemString = null;

        for(int i = 0; i < tempArray.size(); i++) {
            JsonObject object = tempArray.get(i).getAsJsonObject();

            tempMemString = object.get(Key).getAsString();
            if(Value.equals(tempMemString)) {
                idx = i;
                break;
            }
        }

        return idx;
    }

    public void insert(String Username, String Password) {
        JsonObject object = new JsonObject();
        object.addProperty(getKeyUsername(), Username);
        object.addProperty(getKeyPassword(), Password);
        object.addProperty(getKeyRoles(), Roles.Other.toString());
        tempArray.add(object);
    }
}
