package hu.ait.android.todorecylerview.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private long todoId;

    @ColumnInfo(name = "todo_title")
    private String todoTitle;
    @ColumnInfo(name = "create_date")
    private String createDate;
    @ColumnInfo(name = "done")
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

    public long getTodoId() {
        return todoId;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }
}
