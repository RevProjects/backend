package com.revature.controllers;

import com.revature.models.Users;
import com.revature.services.GamesService;
import com.revature.services.UserService;
import com.revature.models.Games;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class GamesController {
    @Autowired
    private GamesService gamesService;
    @Autowired
    private UserService userService;


    //Handler to retrieve all games in the repository
    @GetMapping("/games")
    public ResponseEntity<List<Games>> getAllGames(){
        List<Games> games = gamesService.getAllGames();
        return ResponseEntity.status(200).body(games);
        }

        //Handler to process new games to the repository
    @PostMapping("/games")
    public @ResponseBody ResponseEntity<Games> newGames(@RequestBody Games games){
        Games gm = gamesService.newGames(games);
        if(gm != null){
            return ResponseEntity.status(200).body(gm);
        }
        return ResponseEntity.status(400).body(null);
    }

    //Retrieve specific game by name
    @GetMapping("/games/{gameName}")
    public ResponseEntity<Games> getGamesByGameName(@PathVariable String gameName){
        Games game = gamesService.getGamesByGameName(gameName);
        if(game!=null){
            return ResponseEntity.status(200).body(game);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    /*
    Handler to update a games' owner in case purchased by another company

    Requires game entity to update
    Returns gameName and Status 200 if successful
     */
    @PatchMapping("/games/{gameName}")
    public ResponseEntity<String> updateGames(@RequestBody Games games, @PathVariable String gameName){
        games.setGameName(gameName);
        int updated = gamesService.updateGames(games);
        if(updated == 1){
            return ResponseEntity.status(200).body(gameName);
        }
        return ResponseEntity.status(400).body(null);
    }

    //Handler to retrieve games owned by a specific user
    @GetMapping("users/{userid}/games")
    public ResponseEntity<List<Games>> getGamesByUserid(@PathVariable Long userid){
        List<Games> result = userService.getUserById(userid).getGames();
        return ResponseEntity.status(200).body(result);
    }

    /*
    Handler to assign user to a specific _NEW_ game given gameName
     */
    @PostMapping("users/{userid}/games")
    public ResponseEntity<Games> assignUser(@RequestBody Games games, @PathVariable Integer userid){
        Users us = userService.getUserById(Long.valueOf(userid));
        Games updated = gamesService.assignUser(games, userid);
        if(updated != null){
            return ResponseEntity.status(400).body(updated);
        }
        return ResponseEntity.status(200).body(updated);
    }

    /*
    Handler to delete game from the database.
     */
    @DeleteMapping("/games/{gameId}")
    public ResponseEntity<Integer> deleteGame(@PathVariable Integer gameId){
        int updated = gamesService.deleteGame(gameId);
        if(updated == 1){
            return ResponseEntity.status(200).body(gameId);
        }
        return ResponseEntity.status(200).body(null);
    }


}
