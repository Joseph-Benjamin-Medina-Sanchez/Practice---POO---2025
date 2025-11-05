package model;

public class Chicken {
    private int id;
    private String name;
    private String color;
    private int age;
    private boolean isMolting;

    public Chicken(int id, String name, String color, int age, boolean isMolting) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.age = age;
        this.isMolting = isMolting;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getColor() { return color; }
    public int getAge() { return age; }
    public boolean isMolting() { return isMolting; }

    public void setName(String name) { this.name = name; }
    public void setColor(String color) { this.color = color; }
    public void setAge(int age) { this.age = age; }
    public void setIsMolting(boolean isMolting) { this.isMolting = isMolting; }

    @Override
    public String toString() {
        return String.format(
            "Chicken ID: %d\n   Name: %s\n   Color: %s\n   Age: %d\n   Is Molting: %b",
            id, name, color, age, isMolting
        );
    }
}
