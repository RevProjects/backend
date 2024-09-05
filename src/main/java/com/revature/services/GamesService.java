package com.revature.services;
import com.revature.models.Games;
import com.revature.models.Users;
import com.revature.repositories.GamesRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.GamesService;
import com.revature.services.UserService;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GamesService {

    @Autowired
    private GamesRepository gamesRepository;
    private UserRepository userRepository;

    @Autowired
    public GamesService(GamesRepository gamesRepository, UserRepository userRepository) {
        this.gamesRepository = gamesRepository;
        this.userRepository = userRepository;
    }

    //Retrieve all games in the repository
    public List<Games> getAllGames() {
        return gamesRepository.findAll();
    }

    //Save new game to the repository
    public Games newGames(Games games) {
        return gamesRepository.save(games);
    }

    //Retrieve games by gamName
    public Games getGamesByGameName(String gameName) {
        Optional<Games> game = gamesRepository.findGamesByGameName(gameName);
        if (game.isPresent()) {
            return game.get();
        }
        return null;
    }

    /*
    Update a game's owner given game entity
    Returns a 1 if update is successful
     */
    public int updateGames(Games games) {
        String gameName = games.getGameName();
        String owner = games.getOwner();
        Optional<Games> optionalGames = gamesRepository.findGamesByGameName(gameName);

        if (optionalGames.isEmpty()) {
            return 0;
        }

        Games updated = optionalGames.get();
        updated.setOwner(owner);
        gamesRepository.save(updated);

        return 1;
    }
    /*
        Would really like the updateGames() to take in an owner and return the entire updated game
        rather than the gameName; however, would take a decent amount of adjustments.
     */

    /*
    Method to assign a _NEW_ game an userid
    Game can not already exist in the database
     */
    public Games assignUser(Games games, Integer userid){
        Optional<Users> optionalUsers = userRepository.findUsersById(Long.valueOf(userid));
        Users users = optionalUsers.get();
        users.setId(userid);
        games.setUsers(users);
        return gamesRepository.save(games);
    }

    public Integer deleteGame(Integer gameId){
        Optional<Games> optionalGames = gamesRepository.findById(gameId);
        if(optionalGames.isPresent()){
            gamesRepository.deleteById(gameId);
            return 1;
        }
        return 0;
    }

}