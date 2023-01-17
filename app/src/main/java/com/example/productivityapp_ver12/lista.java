package com.example.productivityapp_ver12;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class  lista{
    public static final String TABLE_NAME="lista";
    public static final String FIELD_ID="id";
    public static final String FIELD_IME="imeL";
    public static final String FIELD_TASK="spisak";


    private String id;
    private String imeL;
    private String spisak;

    public String getId() {
        return id;
    }

    public String getImeL() {
        return imeL;
    }

    public String getSpisak() {
        return spisak;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImeL(String imeL) {
        this.imeL = imeL;
    }

    public void setSpisak(String spisak) {
        this.spisak = spisak;
    }

    public lista(String id, String imeL, String spisak) {
        this.id = id;
        this.imeL = imeL;
        this.spisak = spisak;
    }

    public lista() {

    }

    public static lista fromJson(JSONObject o){
        lista l = new lista();

        try{
            if(o.has("id")){
                l.setId(o.getString("id"));
            }
            if(o.has("ime")){
                l.setImeL(o.getString("ime"));
            }if(o.has("taskovi")){
                l.setImeL(o.getString("taskovi"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public String toString() {
        return "lista{" +
                "id='" + id + '\'' +
                ", imeL='" + imeL + '\'' +
                ", spisak='" + spisak + '\'' +
                '}';
    }
}

