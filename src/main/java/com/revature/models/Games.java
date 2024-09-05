package com.revature.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
@Table(name="games")
//@AllArgsConstructor
//@NoArgsConstructor
public class Games {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="gameid")
    private Integer gameId;
    @Column(name="gamename")
    private String gameName;
    @Column(name="owner")
    private String owner;

    @Autowired
    public Games(){ }

    @Autowired
    public Games(String gameName){
        this.gameName = gameName;
    }

    @Autowired
    public Games(Integer gameId, String gameName){
        this.gameId = gameId;
        this.gameName = gameName;
    }

    @Autowired
    public Games(Integer gameId, String gameName, String owner){
        this.gameId = gameId;
        this.gameName = gameName;
        this.owner = owner;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "gid", referencedColumnName = "userid")
    private Users users;
}
