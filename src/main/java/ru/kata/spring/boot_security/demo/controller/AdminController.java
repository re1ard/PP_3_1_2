package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;

    //@Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }
    //показать всех пользователей
    @GetMapping("/")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "show_users";
    }
    //добавить пользователя через страницу редактирования
    @GetMapping("/adduser")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        return "editor_user";
    }
    //страница пользователя
    @GetMapping("/showuser/{id}")
    public String userInfo(@PathVariable("id") long id, Model model) {
        User currentUser = userService.getUser(id);
        model.addAttribute("userInfo", currentUser);
        return "show_user";
    }
    //callback для добавки пользователя
    @PostMapping("/adduser")
    public String createUser(@ModelAttribute("newUser") User user) {
        userService.save(user);
        return "redirect:/admin/";
    }
    //удалить пользователя по ид
    @RequestMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }
    //обновить пользователя
    @RequestMapping("/updateuser/{id}")
    public String userInfo(Model model, @PathVariable("id") long id) {
        User currentUser = userService.getUser(id);
        model.addAttribute("newUser", currentUser);
        return "editor_user";
    }
}
