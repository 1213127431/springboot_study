package com.tj.controller;

import com.tj.dal.entity.User;
import com.tj.dal.repo.UserMapper;
import com.tj.model.response.Response;
import com.tj.thread.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户controller
 *
 * @author tangjie
 * @version 0.0.1
 * @since 2022/5/2 21:32
 **/
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private ThreadPoolManager threadPoolManager;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/getUser")
    public Response<User> getUser(@RequestParam Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return Response.buildSuccessResponse(user);
    }

    @PostMapping(value = "/updateUser")
    public Response<Object> updateUser(@RequestBody User user) {
        userMapper.updateByPrimaryKey(user);
        return Response.buildSuccessResponse(null);
    }

    @PostMapping(value = "/addUser")
    public Response<Object> addUser(@RequestBody User user) {
        userMapper.insert(user);
        return Response.buildSuccessResponse(null);
    }

    @GetMapping(value = "/batchSave")
    public Response<Object> batchSave() {

        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            threadPoolManager.getExecutorService().submit(() -> {
                User user = new User();
                user.setName("唐杰" + finalI);
                user.setAge(finalI);
                user.setAddress("地址" + finalI);
                userMapper.insert(user);
            });
        }

        return Response.buildSuccessResponse(null);
    }
}
