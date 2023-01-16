package com.example.productivityapp_ver12;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class  lista{
    private String id;
    private String imeL;
    //private ArrayList<String> spisak;

    public String getId() {
        return id;
    }

    public String getImeL() {
        return imeL;
    }

   // public ArrayList<String> getSpisak() {
   //     return spisak;
   // }

    public void setId(String id) {
        this.id = id;
    }

    public void setImeL(String imeL) {
        this.imeL = imeL;
    }

   // public void setSpisak(ArrayList<String> spisak) {
   //     this.spisak = spisak;
   // }

    public lista(String id, String imeL) {
        this.id = id;
        this.imeL = imeL;
       // this.spisak = spisak;
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l;
    }
    @Override
    public String toString() {
        return "liste{" +
                "id='" + id + '\'' +
                ", imeL='" + imeL + '\'' +
                '}';
    }
}

