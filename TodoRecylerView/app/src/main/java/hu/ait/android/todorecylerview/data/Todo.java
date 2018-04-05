package hu.ait.android.todorecylerview.data;

public class Todo {

    private String todoTitle;
    private String createDate;
    private boolean done;

    public Todo(String todoTitle, String createDate, boolean done) {
        this.todoTitle = todoTitle;
        this.createDate = createDate;
        this.done = done;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
