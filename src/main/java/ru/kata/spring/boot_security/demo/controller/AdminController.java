package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;

    //@Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }
    //показать всех пользователей
    @GetMapping("")
    public String getAllUsers(Model model,
                              Principal principal,
                              @RequestParam(name="show", required = false, defaultValue = "users") String show,
                              @RequestParam(name="id", required = false, defaultValue = "0") long id
    ) {
        model.addAttribute("current_user", userService.findByUsername(principal.getName()));
        model.addAttribute("admin_features", "yes");
        if(show.matches("users")){
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("show", "users");
            model.addAttribute("container", "users_edit");
        } else if (show.matches("new_user")) {
            model.addAttribute("newUser", new User());
            model.addAttribute("show", "new_user");
            model.addAttribute("container", "users_edit");
        } else if (id != 0){
            User currentUser = userService.getUser(id);
            model.addAttribute("userInfo", currentUser);
            model.addAttribute("show", "users");
            model.addAttribute("container", "show_user");
        }
        //return "show_users";
        return "bootstrap/main";
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
    public String deleteUser(@PathVariable("id") long id, Principal principal) {
        User user = userService.getUser(id);
        if (user == null) {
            return "redirect:/admin/";
        }
        if(principal.getName() == user.getUsername()) {
            return "redirect:/admin/";
        }

        userService.deleteUser(id);
        return "redirect:/admin/";
    }
    //обновить пользователя
    @RequestMapping("/updateuser/{id}")
    public String userInfo(Model model, @PathVariable("id") long id) {
        User currentUser = userService.getUser(id);
        currentUser.setPassword("");
        model.addAttribute("newUser", currentUser);
        return "editor_user";
    }
}
