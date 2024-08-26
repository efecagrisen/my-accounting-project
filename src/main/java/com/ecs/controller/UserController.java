package com.ecs.controller;

import com.ecs.dto.UserDto;
import com.ecs.service.CompanyService;
import com.ecs.service.RoleService;
import com.ecs.service.SecurityService;
import com.ecs.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
            model.addAttribute("users",userService.listAdminUsers("Admin"));
        }else {
            model.addAttribute("users", userService.findUsersByCompanyIdOrderByRoleIdAsc(companyId));
        }//todo isOnlyAdmin doesn't work
        return "/user/user-list";
    }

    @GetMapping("/create")
    public String createUser(Model model){

            model.addAttribute("newUser", new UserDto());
            model.addAttribute("userRoles",roleService.listRolesByLoggedInUserId(securityService.getLoggedInUser().getId()));

            if (securityService.getLoggedInUser().getId()==1){
                model.addAttribute("companies",companyService.listNonRootCompanies());
                }else {
                    model.addAttribute("companies",companyService.getCompanyDtoByLoggedInUser());
                }
        return "/user/user-create";
    }

    @PostMapping("/create")
    public String insertUser(@Valid @ModelAttribute ("newUser") UserDto user, BindingResult bindingResult, Model model){

        boolean emailExists = userService.checkUsernameExists(user.getUsername());

        if (bindingResult.hasErrors()){
            if (emailExists){
                bindingResult.rejectValue("username","","A user with this email already exists. Please try with different email.");
            }

            model.addAttribute("userRoles",roleService.listRolesByLoggedInUserId(securityService.getLoggedInUser().getId()));
            if (securityService.getLoggedInUser().getId()==1){
                model.addAttribute("companies",companyService.listNonRootCompanies());
            }else {
                model.addAttribute("companies",companyService.getCompanyDtoByLoggedInUser());
            }

            return "/user/user-create";
        }
        userService.save(user);
        return "redirect:/users/list";
    }


    @GetMapping("/update/{id}")
    public String editUser(Model model, @PathVariable ("id") Long id){

        model.addAttribute("user",userService.findById(id));
        model.addAttribute("userRoles",roleService.listAllRolesOtherThanRoot());
        if (securityService.getLoggedInUser().getId()==1){
            model.addAttribute("companies",companyService.listNonRootCompanies());
        }else {
            model.addAttribute("companies",companyService.getCompanyDtoByLoggedInUser());
        }

        return "/user/user-update";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute ("user") UserDto user,BindingResult bindingResult, Model model){

        boolean emailExists = userService.checkUsernameExists(user.getUsername());

        if (bindingResult.hasErrors()){
//            if (emailExists){
//                bindingResult.rejectValue("username","","A user with this email already exists. Please try with different email.");
//            }
//            if (userService.checkIsUserOnlyAdmin(user,user.getRole())){
//                bindingResult.addError(new ObjectError("user","This user is the only admin of the company name company. Please, do not change role or company"));
//            }

            model.addAttribute("userRoles",roleService.listRolesByLoggedInUserId(securityService.getLoggedInUser().getId()));
            if (securityService.getLoggedInUser().getId()==1){
                model.addAttribute("companies",companyService.listNonRootCompanies());
            }else {
                model.addAttribute("companies",companyService.getCompanyDtoByLoggedInUser());
            }

            return "/user/user-update";
        }

        userService.save(user);

        return "redirect:/users/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){

        userService.delete(userService.findById(id));

        return "redirect:/users/list";
    }




}
