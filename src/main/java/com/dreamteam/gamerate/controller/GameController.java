package com.dreamteam.gamerate.controller;

import com.dreamteam.gamerate.DatabaseCon;
import com.dreamteam.gamerate.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GameController {
    @Autowired
    private DatabaseCon databaseCon;

    @GetMapping("/")
    public String index(Model model) {
        List<Game> gameList = databaseCon.getAllGames();
        model.addAttribute("games", gameList);
        System.out.println(gameList);
        return "index";
    }
    @PostMapping("/search")
    public String search(@RequestParam String searchword, Model model){
        List<Game> gameList = databaseCon.searchGames(searchword);
        model.addAttribute("search",gameList);
        return "result";
    }

        @GetMapping("/index")
    public String index1(Model model){
        List<Game> gameList = databaseCon.getAllGames();
        model.addAttribute("games" ,gameList);
        System.out.println(gameList);
        return "index";
    }
    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping("/login")
    public String checkIfLogin(@RequestParam String username, @RequestParam String password, HttpSession httpSession) {
        httpSession.setAttribute("isLogin", true);
        databaseCon.checkLogin(username, password);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse res) {
        session.invalidate();
        Cookie cookie = new Cookie("sessionId", "");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String info(@PathVariable int id, Model model) {
        Game game = databaseCon.getGame(id);
        model.addAttribute("game", game);
        return "info";
    }

    @PostMapping("/info")
    public String checkRating(@RequestParam int id, @RequestParam int star, HttpSession session) {
        if (session.getAttribute("isLogin") != null) {
            databaseCon.storeVote(id, star);

            System.out.println("Nu kom vi hit" + id + star);
        }

        return "redirect:/" + id;
    }


    @GetMapping("/register")
    public String goToRegsiter() {
        return "CreateAccount";
    }

    @PostMapping("/register")
    public String checkRegister(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword) {
        databaseCon.createUser(username, email, password, confirmPassword);
        System.out.println(username + " " + password + " " + confirmPassword);
        return "login";
    }

    @GetMapping("/profile")
    public String profile() {
        return "Profile";
    }

}
