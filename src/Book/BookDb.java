package Book;

import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BookDb {
    private String containFiles;
    private String KeyTitle = "Title";
    private String KeyAuthor = "Author";
    private String KeyPublishingYear = "PublishingYear";
    private String KeyRating = "Rating";
    private String KeyIntroduce = "Introduce";
    private String KeyRemainingAmount = "RemainingAmount";

    private JsonArray tempArray;

    public String getKeyTitle() {
        return KeyTitle;
    }

    public String getKeyAuthor() {
        return KeyAuthor;
    }

    public String getKeyPublishingYear() {
        return KeyPublishingYear;
    }
    
    public String getKeyRating() {
        return KeyRating;
    }
    public String getKeyIntroduce() {
        return KeyIntroduce;
    }

    public String getKeyRemainingAmount() {
        return KeyRemainingAmount;
    }

    public BookDb(String containFiles) {
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

    public boolean valid(String Key, String Value) {
        int idx = -1;
        boolean status;

        idx = search(Key, Value);
        if (idx == -1)
            status = false;
        else
            status = true;

        return status;
    }

    public void insert(String Title, String Author, int PublishingYear, int RemainingAmount) {
        JsonObject object = new JsonObject();
        object.addProperty(getKeyTitle(), Title);
        object.addProperty(getKeyAuthor(), Author);
        object.addProperty(getKeyPublishingYear(), PublishingYear);
        object.addProperty(getKeyRemainingAmount(), RemainingAmount);
        tempArray.add(object);
    }

    public boolean insertBorrow(String Username, String Title, String Author) {
        boolean success = false;

        JsonArray array = readAll();

        if (!valid(getKeyTitle(), Title)) {
            JsonObject object = new JsonObject();
            object.addProperty("Username", Username);
            object.addProperty(getKeyTitle(), Title);
            object.addProperty(getKeyAuthor(), Author);
            tempArray.add(object);

            write();
            success = true;
        }

        return success;
    }

    public boolean insertRequest(String Username, String Title, String Author) {
        boolean success = false;

        if (!valid(getKeyTitle(), Title)) {
            JsonObject object = new JsonObject();
            object.addProperty("Username", Username);
            object.addProperty(getKeyTitle(), Title);
            object.addProperty(getKeyAuthor(), Author);
            tempArray.add(object);

            write();
            success = true;
        }
        return success;
    }
}
