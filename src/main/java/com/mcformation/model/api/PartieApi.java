package com.mcformation.model.api;

public class PartieApi {

    private Long id;
    private String plan;
    private Long timing;
    private String contenu;
    private String methodologie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Long getTiming() {
        return timing;
    }

    public void setTiming(Long timing) {
        this.timing = timing;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getMethodologie() {
        return methodologie;
    }

    public void setMethodologie(String methodologie) {
        this.methodologie = methodologie;
    }
}
