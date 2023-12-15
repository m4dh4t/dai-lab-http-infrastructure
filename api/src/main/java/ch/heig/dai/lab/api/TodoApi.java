package ch.heig.dai.lab.api;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class TodoApi {
    private static final List<String> todoList = new ArrayList<>();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(80);

        app.get("/api", TodoApi::getAllTodos);
        app.delete("/api", TodoApi::deleteAllTodos);
        app.post("/api", TodoApi::createTodo);
        app.get("/api/{id}", TodoApi::getTodo);
        app.put("/api/{id}", TodoApi::updateTodo);
        app.delete("/api/{id}", TodoApi::deleteTodo);

        Runtime.getRuntime().addShutdownHook(new Thread(app::stop));
    }

    private static void getAllTodos(Context ctx) {
        ctx.json(todoList);
    }

    private static void createTodo(Context ctx) {
        String task = ctx.body();
        todoList.add(task);
        ctx.status(201).json("Todo created successfully");
    }

    private static void getTodo(Context ctx) {
        String todoId = ctx.pathParam("id");
        int id = Integer.parseInt(todoId);

        if (id >= 0 && id < todoList.size()) {
            ctx.json(todoList.get(id));
        } else {
            ctx.status(404).json("Todo not found");
        }
    }

    private static void updateTodo(Context ctx) {
        String todoId = ctx.pathParam("id");
        int id = Integer.parseInt(todoId);

        if (id >= 0 && id < todoList.size()) {
            String updatedTask = ctx.body();
            todoList.set(id, updatedTask);
            ctx.json("Todo updated successfully");
        } else {
            ctx.status(404).json("Todo not found");
        }
    }

    private static void deleteTodo(Context ctx) {
        String todoId = ctx.pathParam("id");
        int id = Integer.parseInt(todoId);

        if (id >= 0 && id < todoList.size()) {
            todoList.remove(id);
            ctx.json("Todo deleted successfully");
        } else {
            ctx.status(404).json("Todo not found");
        }
    }

    private static void deleteAllTodos(Context ctx) {
        todoList.clear();
        ctx.json("All todos deleted successfully");
    }
}