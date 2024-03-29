package com.ybzbcq.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ybzbcq.model.InfoUser;
import com.ybzbcq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 * @Description 用户控制器
 * @since 2019-06-06 14:09
 */

@Controller
@RequestMapping("/user/")
public class InfoUserController {

    @Reference
    private UserService userService;

    @RequestMapping("getInfoById")
    @ResponseBody
    public InfoUser userList(Integer id) {
        InfoUser infoUser = userService.selectByPrimaryKey(id);
        return infoUser;
    }

    @RequestMapping("list")
    public String getUserList(Model model, Integer currentPage) {

        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }

        Integer pageSize = 5;

        if (pageSize == null || currentPage < 1) {
            pageSize = 5;
        }

        int i = userService.qryCount(); // 查询数据总数
        int totalPages = i / pageSize;

        int left = i % pageSize;
        if (left > 0) {
            totalPages = totalPages + 1;
        }


        if(currentPage > totalPages){
            currentPage = totalPages;
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();

        //  limit 开始行数
        paramMap.put("currentPage", (currentPage - 1) * pageSize);
        paramMap.put("pageSize", pageSize);
        List<InfoUser> infoUsers = userService.selectByMultCondition(paramMap);

        model.addAttribute("infoUsers", infoUsers);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        return "infoUser";
    }

    @RequestMapping("delete")
    public String deleteUser(Integer id){
        if(!StringUtils.isEmpty(id)){
            userService.deleteByPrimaryKey(id);
        }
        return "redirect:/user/list";
    }


    @RequestMapping("toAddUser")
    public String toAddUser(){
        return "addUser";
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public String addUser(InfoUser infoUser){
        if(infoUser.getId() == null){
            userService.insertSelective(infoUser);
        }else {
            userService.updateByPrimaryKeySelective(infoUser);
        }
        return "redirect:/user/list";
    }

    @RequestMapping("toUpdateUser")
    public String toUpdateUser(Model model, @RequestParam("id") Integer id){
        InfoUser user = userService.selectByPrimaryKey(id);
        model.addAttribute("user", user);
        return "addUser";
    }
}