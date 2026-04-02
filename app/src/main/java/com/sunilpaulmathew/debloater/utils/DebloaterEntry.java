package com.sunilpaulmathew.debloater.utils;

import org.json.JSONArray;

import java.io.Serializable;

/*
 * Created by sunilpaulmathew <sunil.kde@gmail.com> on March 31, 2026
 */
public class DebloaterEntry implements Serializable {

    private final JSONArray dependencies, neededBy, labels;
    private final String packageName, list, description,  removal;

    public DebloaterEntry(String packageName, String list, String description, JSONArray dependencies, JSONArray neededBy, JSONArray labels, String removal) {
        this.packageName = packageName;
        this.list = list;
        this.description = description;
        this.dependencies = dependencies;
        this.neededBy = neededBy;
        this.labels = labels;
        this.removal = removal;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getList() {
        return list;
    }

    public JSONArray getDependencies() {
        return dependencies;
    }

    public String getDescription() {
        return description;
    }

    public JSONArray neededBy() {
        return neededBy;
    }

    public JSONArray getLabels() {
        return labels;
    }

    public String getRemovalStatus() {
        return removal;
    }

}