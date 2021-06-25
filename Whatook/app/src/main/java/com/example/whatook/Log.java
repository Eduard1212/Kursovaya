package com.example.whatook;

public class Log {

    private long id;
    private long userId;
    private long actionId;
    private long recipeId;
    private String user;
    private String action;
    private String recipe;
    private String time;

    Log(long id, long userId, long actionId, long recipeId, String user, String action, String recipe, String time){
        this.id = id;
        this.userId = userId;
        this.actionId = actionId;
        this.recipeId = recipeId;
        this.user = user;
        this.action = action;
        this.recipe = recipe;
        this.time = time;
    }
    public long getId() {
        return id;
    }
    public long getUserId() {
        return userId;
    }
    public long getActionId() {
        return actionId;
    }
    public long getRecipeId() {
        return recipeId;
    }
    public String getUser() {
        return user;
    }
    public String getAction() {
        return action;
    }
    public String getRecipe() {
        return recipe;
    }
    public String getTime() {
        return time;
    }
}