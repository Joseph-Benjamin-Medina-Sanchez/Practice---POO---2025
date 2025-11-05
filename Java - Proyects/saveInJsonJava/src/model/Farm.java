package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Farm {
    private String filePath;
    private List<Coop> coops;

    public Farm(String filePath) {
        this.filePath = filePath;
        this.coops = loadData();
    }

    public List<Coop> getCoops() { return coops; }

    public void addCoop(Coop coop) {
        coops.add(coop);
        saveData();
    }

    public Coop findCoopById(int id) {
        for (Coop c : coops) if (c.getId() == id) return c;
        return null;
    }

    public void removeCoop(int id) {
        coops.removeIf(c -> c.getId() == id);
        saveData();
    }

    public void saveData() {
        try (FileWriter writer = new FileWriter(filePath)) {
            new Gson().toJson(coops, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Coop> loadData() {
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<ArrayList<Coop>>() {}.getType();
            List<Coop> loaded = new Gson().fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
