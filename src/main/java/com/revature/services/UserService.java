package com.revature.services;
import com.revature.models.Users;
import com.revature.models.Games;
import com.revature.repositories.UserRepository;
import com.revature.repositories.GamesRepository;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GamesRepository gamesRepository;

//@Autowired
    public UserService(UserRepository userRepository, GamesRepository gamesRepository) {
        this.userRepository = userRepository;
        this.gamesRepository = gamesRepository;
    }

    public void persistUsers(Users users) {
        this.userRepository.save(users);
    }


    public List<Users> getAllUsers() throws Exception {
        return userRepository.findAll();
    }

    public Optional<Users> checkIfUserNameExists(String name) throws Exception {
        Optional<Users> optionalUsers = userRepository.findUsersByName(name);
        return optionalUsers;
    }


    public Users login(String username, String password) throws AuthenticationException {
        Optional<Users> user = userRepository.findUsersByNameAndPassword(username, password);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public Users getUserByName(String name) {
        Optional<Users> user = userRepository.findUsersByName(name);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public Users getUserById(Long userid) {
        Optional<Users> user = userRepository.findUsersById(userid);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    /*
    Pretty sure we can delete all this out, but wasn't really sure so can double check

    public Users getGamesByUserId(Long userid){
        Optional<Users> user=userRepository.findGamesById(userid);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    public List<Games> getGamesByUserid(Long userid) {
        List<Games> game = gamesRepository.findAll();
        List<Games> userGames = new ArrayList<Games>();

        for(Games games : game){
            //need to check if gid == userid then return games that are assosciated with this
            if((long)(games.getGid()) == userid){
                userGames.add(games);
            }
        }
        return userGames;
    }
     */

}
