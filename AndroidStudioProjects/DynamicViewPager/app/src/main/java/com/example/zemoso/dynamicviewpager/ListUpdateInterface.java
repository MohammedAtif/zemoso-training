package com.example.zemoso.dynamicviewpager;

/**
 * Created by zemoso on 18/11/16.
 */

public interface ListUpdateInterface {
    void addItemToList(String item);
    void removeItemFromList(String item);
    void addItemToList(int position, String item);
    void removeItemFromList(int position);
}
