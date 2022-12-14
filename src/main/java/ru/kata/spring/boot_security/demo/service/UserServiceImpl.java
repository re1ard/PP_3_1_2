package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Lazy
    @Autowired
    public UserServiceImpl( UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void check_first_start() {
        if(roleRepository.RolesCount() == 0) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_USER"));
        }

        if(userRepository.UsersCount() == 0) {
            Collection<Role> admin_roles = new ArrayList<>();
            admin_roles.add(roleRepository.getById(1L));
            userRepository.save(new User("admin",passwordEncoder.encode("admin"),"admin@service.com", admin_roles, "Administrator", "None", (byte)0));
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Transactional
    public void save(User user) {
        //Role user fix
        if(user.getId() == 0) {
            Role user_role = roleRepository.getById(2L);
            Collection<Role> user_roles = user.getRoles();
            if (user_roles == null) {
                user_roles = new ArrayList<>();
            }
            user_roles.add(user_role);
            user.setRoles(user_roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            if (user.getPassword() != "") {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                user.setPassword(userRepository.getById(user.getId()).getPassword());
            }
            user.setRoles(userRepository.getById(user.getId()).getRoles());
        }

        userRepository.save(user);
    }
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        //???? ???????????????????? ??????????????  ???????????? user, ?????????????????? ???????????? "User account is locked"
        //upd 2, ???? ????????????????????, ???????????? ???????????? ???????????? ???? ???????????????????? ?????????? ????????????
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
               user.getPassword(), user.getRoles());
        //return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}