package com.ybzbcq.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ybzbcq.model.InfoUser;
import com.ybzbcq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public InfoUser userList(Integer id){
        InfoUser infoUser = userService.selectByPrimaryKey(id);
        return infoUser;
    }

}