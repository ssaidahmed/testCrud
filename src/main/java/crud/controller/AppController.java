package crud.controller;

import com.google.common.collect.Lists;
import crud.form.UserGrid;
import crud.model.User;
import crud.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class AppController {


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }


    @RequestMapping(value = {"/","/list"}, method = RequestMethod.GET)
    public String users(Model uiModel){

        List<User> users = userService.findAll();
        uiModel.addAttribute("user", new User());
        uiModel.addAttribute("users", users);
        return "/list";

    }
    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "rows", required = false) Integer rows,
                             @RequestParam(value = "sidx", required = false) String sortBy,
                             @RequestParam(value = "sord", required = false) String order){
        Sort sort = null;
        String orderBy = sortBy;
        if(orderBy != null && orderBy.equals("getCreateDateString")){
            orderBy = "createDate";
        }
        if (orderBy != null && order != null) {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else {
                sort = new Sort(Sort.Direction.ASC, orderBy);
            }
        }
        PageRequest pageRequest = null;
        if (sort != null){
            pageRequest = new PageRequest(page - 1, rows, sort);
        }else {

            pageRequest = new PageRequest(page - 1, rows);
        }
        Page<User> userPage = userService.findAllByPage(pageRequest);

        UserGrid userGrid = new UserGrid();
        userGrid.setCurrentPage(userPage.getNumber() + 1);
        userGrid.setTotalPages(userPage.getTotalPages());
        userGrid.setTotalRecords(userPage.getTotalElements());
        userGrid.setUserData(Lists.newArrayList(userPage.iterator()));
        return userGrid;
    }
    @RequestMapping(value = "/search/{name}")
    public String searchUser(@PathVariable("name") String name, Model model){
        User user = userService.findByName(name);
        model.addAttribute("search", user);
        return "/list";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, BindingResult result){

        if(user.getId() == 0){
            this.userService.addUser(user);
        }else{
            this.userService.save(user);
        }

        return "redirect:/list";
    }
    @RequestMapping(value = {"/remove/{id}"})
    public String removeUser(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/list";
    }
    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("users", userService.findAll());
        return "/list";
    }
    @RequestMapping(value = "/editpost/{id}" , method = RequestMethod.POST)
    public String updateUser(User user){
        userService.save(user);
        return "/list";
    }

}
