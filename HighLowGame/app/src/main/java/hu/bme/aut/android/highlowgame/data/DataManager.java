package hu.bme.aut.android.highlowgame.data;

public class DataManager {

    private DataManager(){}

    private static DataManager instance = null;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }


    private int age;
    private int coutner = 5;
    private String name;



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCoutner() {
        return coutner;
    }

    public void setCoutner(int coutner) {
        this.coutner = coutner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
