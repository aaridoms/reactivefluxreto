package com.ntt.bootcamp.reactivefluxreto.controller;

import com.ntt.bootcamp.reactivefluxreto.Model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private ArrayList<User> userList = new ArrayList<User>();

    public UserController() {
        userList.add(new User("Angel", "aaridom", "psw1"));
        userList.add(new User("Diego", "degava", "psw2"));
        userList.add(new User("ADMIN", "admin", "adminpsw"));
    }

    @GetMapping(path = "/login", produces = "text/event-stream")
    public Flux<User> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

        List<User> users = userList.stream().filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password)).collect(Collectors.toList());

        Flux<User> flux = Flux.fromIterable(userList).delayElements(Duration.ofSeconds(1));

        return flux;
    }

    @GetMapping(path = "/adduser")
    public void addUser(@RequestParam(value = "name") String name, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        userList.add(new User(name, username, password));
    }
}
