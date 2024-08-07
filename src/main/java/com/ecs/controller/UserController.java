package com.ecs.controller;

import com.ecs.dto.UserDto;
import com.ecs.service.CompanyService;
import com.ecs.service.RoleService;
import com.ecs.service.SecurityService;
import com.ecs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    private final RoleService roleService;
    private final CompanyService companyService;
    private final SecurityService securityService;

    public UserController(UserService userService, RoleService roleService, CompanyService companyService, SecurityService securityService) {
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
        this.securityService = securityService;
    }


    @GetMapping("/list")
    public String listCompanyUsers(Model model){

        Long companyId = securityService.getLoggedInUser().getCompany().getId();
        model.addAttribute("roles",roleService.listAllRoles());
        if (securityService.getLoggedInUser().getId()==1){
            model.addAttribute("users",userService.listAllUsers());
        }else {
            model.addAttribute("users", userService.listUsersByCompanyId(companyId));
        }
        return "/user/user-list";
    }

    @GetMapping("/create")
    public String createUser(Model model){

                model.addAttribute("newUser", new UserDto());
                model.addAttribute("userRoles",roleService.listAllRolesOtherThanRoot());

            if (securityService.getLoggedInUser().getId()==1){
                model.addAttribute("companies",companyService.listAllCompanies());
                }else {
                    model.addAttribute("companies",companyService.getCompanyDtoByLoggedInUser());
                }
        return "/user/user-create";
    }

    @PostMapping("/create")
    public String insertUser(@ModelAttribute ("user") UserDto user, BindingResult bindingResult, Model model){

//        if (bindingResult.hasErrors()){
//
//            model.addAttribute("userRoles",roleService.listAllRoles());
//            model.addAttribute("companies",companyService.listAllCompanies());
//
//            return "/user/user-create";
//        }

        userService.save(user);

        return "redirect:/users/list";
    }


    @GetMapping("/update/{id}")
    public String editUser(Model model, @PathVariable ("id") Long id){

        model.addAttribute("user",userService.findById(id));
        model.addAttribute("userRoles",roleService.listAllRolesOtherThanRoot());
        model.addAttribute("companies",securityService.getLoggedInUser().getCompany());

        return "/user/user-update";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute ("user") UserDto user){

        userService.save(user);

        return "redirect:/users/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){

        userService.delete(userService.findById(id));

        return "redirect:/users/list";
    }




}
