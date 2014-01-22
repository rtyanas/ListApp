package com.fuzz.simpleapp;

import java.util.ArrayList;

public class Model {

    public static ArrayList<Item> Items;

    public static void LoadModel() {

        Items = new ArrayList<Item>();
        Items.add(new Item(1, "hiking_trail.png", "Hiking Trail"));
        Items.add(new Item(2, "sandyhook_panorama_icon.png", "Sandy Hook Storm"));
        Items.add(new Item(3, "hiking_the_cat.png", "Hiking the Catamaran"));
//        Items.add(new Item(4, "ic_action_line_chart.png", "Line Chart"));
//        Items.add(new Item(5, "ic_action_location_2.png", "Location"));
//        Items.add(new Item(6, "ic_action_news.png", "News"));
//        Items.add(new Item(7, "ic_action_stamp.png", "Stamp"));

    }

    public static Item GetbyId(int id){

        for(Item item : Items) {
            if (item.Id == id) {
                return item;
            }
        }
        return null;
    }

    public static ArrayList<String> GetNameList(){
    	ArrayList<String> names = new ArrayList<String>();
        for(Item item : Items) {
        	names.add(item.Name);
        }
        return names;
    }

}