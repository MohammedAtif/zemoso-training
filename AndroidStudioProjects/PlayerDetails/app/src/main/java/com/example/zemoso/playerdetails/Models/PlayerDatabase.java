package com.example.zemoso.playerdetails.Models;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zemoso on 21/11/16.
 */

public class PlayerDatabase extends RealmObject {
    @PrimaryKey
    private Long id;

    public PlayerDatabase() {
    }

    public Long getId() {
        return id;
    }

    private String playerName;
    private String imageUrl;
    private int playerAge;
    private int rank;

    public PlayerDatabase(Long id, String playerName, String imageUrl, int playerAge, int rank) {
        this.id = id;
        this.playerName = playerName;
        this.imageUrl = imageUrl;
        this.playerAge = playerAge;
        this.rank = rank;
    }
}
