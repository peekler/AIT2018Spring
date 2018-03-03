package hu.ait.android.multiactivitydemo.data;

public class DataManager {


    private DataManager() {
    }

    private static DataManager instance = null;

    public static DataManager getInstance() {
        if (instance==null) {
            instance = new DataManager();
        }

        return instance;
    }

    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
