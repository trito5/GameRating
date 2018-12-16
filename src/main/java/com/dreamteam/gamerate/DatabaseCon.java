package com.dreamteam.gamerate;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class DatabaseCon {
    private Connection connection = connect("jdbc:mysql://localhost:3306/dreams?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");



    private Connection connect(String url) {
        System.out.println("Creating connection..");
        try {
            System.out.println("uppkopplad");
            return DriverManager.getConnection(url, "", "");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean createUser(String username,String email, String password, String confirmPassword){
        if (!password.equals(confirmPassword)){
            return false;
        }
        try {
            String query = "Insert into users (name, password,email) values(?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,username);
            stmt.setString(2,password);
            stmt.setString(3,email);
            if (stmt.executeUpdate()!=0){
                System.out.println("User inserted");
                return true;
            }else {
                return false;
            }


        }catch (SQLException e){
            System.out.println("något gick fel" + e);
        }
        return false;
    }

    public List<Game> searchGames(String name) {
        List<Game> gameList = new ArrayList<>();

        try {
            String query = "select * from games where name like '%"+name+"%'";
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                gameList.add(new Game(resultSet.getInt("ID"),
                        resultSet.getString("Name"),resultSet.getString("Description"),
                        (double)resultSet.getInt("totalScore")/ resultSet.getInt("numberOfVotes"),resultSet.getString("image"),
                        resultSet.getInt("totalScore"),resultSet.getInt("numberOfVotes")));
            }


        }catch (SQLException e){
            System.out.println("något gick fel" + e);
        }
        gameList.sort(Comparator.comparingDouble(Game::getRating));
        Collections.reverse(gameList);
        return gameList;
    }


    public List<Game> getAllGames() {
        List<Game> gameList = new ArrayList<>();

            try {
                String query = "select * from games";
                PreparedStatement stmt = connection.prepareStatement(query);

                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()){
                    gameList.add(new Game(resultSet.getInt("ID"),
                            resultSet.getString("Name"),resultSet.getString("Description"),
                            (double)resultSet.getInt("totalScore")/ resultSet.getInt("numberOfVotes"),resultSet.getString("image"),
                            resultSet.getInt("totalScore"),resultSet.getInt("numberOfVotes")));
                }


            }catch (SQLException e){
                System.out.println("något gick fel" + e);
            }
        gameList.sort(Comparator.comparingDouble(Game::getRating));
            Collections.reverse(gameList);
            return gameList;
        }



    public Game getGame(int id) {


        try {
            String query = "select * from games where ID=? ";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1,id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
               Game game= new Game(resultSet.getInt("ID"),
                        resultSet.getString("Name"),resultSet.getString("Description"),
                        (double)resultSet.getInt("totalScore")/ resultSet.getInt("numberOfVotes"),resultSet.getString("image"),
                       resultSet.getInt("totalScore"),resultSet.getInt("numberOfVotes"));
               return game;
            }


        }catch (SQLException e){
            System.out.println("något gick fel" + e);
        }

        return new Game();
    }
    public void storeVote(int id, int star){
        Game game = getGame(id);
        System.out.println("Här är votes!" + game.getVotes());
        game.setVotes(game.getVotes()+1);
        game.setTotalScore(game.getTotalScore()+star);
        try {
            String query = "update games set numberOfVotes=?, totalScore=? where ID =? ";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1,game.getVotes());
            stmt.setInt(2,game.getTotalScore());
            stmt.setInt(3,id);

            stmt.executeUpdate();



        }catch (SQLException e){
            System.out.println("något gick fel" + e);
        }

    }
    public boolean checkLogin(String username,String password){
        System.out.println("Nu går vi in");


        try {
            String query = "select * from users where name =? and password =? ";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1,username);
            stmt.setString(2,password);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()){
                System.out.println("Logged in");
                return true;
            }


        }catch (SQLException e){
            System.out.println("något gick fel" + e);
        }
        return false;
    }
}
