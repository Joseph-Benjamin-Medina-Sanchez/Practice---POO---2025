package model;

import java.util.ArrayList;
import java.util.List;

public class Coop {
    private int id;
    private String location;
    private List<Chicken> chickens;

    public Coop(int id, String location) {
        this.id = id;
        this.location = location;
        this.chickens = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getLocation() { return location; }
    public List<Chicken> getChickens() { return chickens; }

    public void addChicken(Chicken chicken) { chickens.add(chicken); }
    public void removeChicken(int id) { chickens.removeIf(c -> c.getId() == id); }

    public Chicken findChickenById(int id) {
        for (Chicken c : chickens) if (c.getId() == id) return c;
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Coop ID: ").append(id).append(" | Location: ").append(location)
          .append(" | Chickens: ").append(chickens.size()).append("\n");
        for (Chicken c : chickens) sb.append("  ").append(c.toString()).append("\n");
        return sb.toString();
    }
}
