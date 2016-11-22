package com.example.zemoso.playerdetails;

import com.example.zemoso.playerdetails.Models.PlayerDatabase;

/**
 * Created by zemoso on 20/11/16.
 */

public interface ListUpdateInterface {
    void addItemToList(PlayerDatabase item);
    void removeItemFromList(String item);
    void addItemToList(int position, PlayerDatabase item);
    void removeItemFromList(int position);
}
