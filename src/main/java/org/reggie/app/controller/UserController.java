package org.reggie.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.reggie.app.common.R;
import org.reggie.app.entity.User;
import org.reggie.app.service.UserService;
import org.reggie.app.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public R<String> login(@RequestBody Map userMap, HttpSession session) {
        log.info("用户登录");

        String sessionCode = (String) session.getAttribute("code");
        String userCode = userMap.get("code").toString();

        if(sessionCode == null || !sessionCode.equals(userCode)) {
            return R.error("验证码错误");
        }

        String phone = userMap.get("phone").toString();


        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        User currentUser = userService.getOne(queryWrapper);
        if (currentUser == null) {
            User user = new User();
            user.setPhone(phone);
            user.setStatus(1);
            userService.save(user);
            session.setAttribute("user",user.getId());
        } else {
            session.setAttribute("user",currentUser.getId());
        }

        return R.success("登录成功");
    }

    public R<String> logout() {
        return R.success("退出成功");
    }

    /***发送短信验证码
     *
     * @param user
     * @return
     */

    @PostMapping("sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        /***生成4位随机数字验证码*/
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));

        String phone = user.getPhone();

        session.setAttribute("code", code);

        log.info(user.toString());

        SMSUtils.sendMessage(phone, code);

        log.info("发送短信验证码");
        return R.success("发送成功");
    }

    @PostMapping
    public R<String> register(User user) {
        log.info("用户注册");
        this.userService.save(user);
        return R.success("注册成功");
    }
}
