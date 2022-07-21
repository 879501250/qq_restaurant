package com.qq.qqrestaurant.controller;

import com.qq.qqrestaurant.common.R;
import com.qq.qqrestaurant.entity.User;
import com.qq.qqrestaurant.service.UserService;
import com.qq.qqrestaurant.utils.SMSUtils;
import com.qq.qqrestaurant.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/sendMsg")
    public R sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            // 随机生成4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info(code);

            // 阿里云发送短信验证码
//            SMSUtils.sendMessage("QQ外卖","",code,phone);

            session.setAttribute(phone,code);
            return R.success(code);
        }
        return R.error("发生短信验证码失败~");
    }

    @PostMapping("/login")
    public R login(@RequestBody Map map, HttpSession session) {
        String phone = map.get("phone").toString();
        if (StringUtils.isNotEmpty(phone)) {
            String code = map.get("code").toString();

            if (session.getAttribute(phone).equals(code)) {
                User user = userService.login(phone);
                session.setAttribute("user", user.getId());
                return R.success(user);
            }

            return R.error("验证码错误~");
        }
        return R.error("登录失败~");
    }

    @PostMapping("/loginout")
    public R loginout(HttpSession session) {
        session.removeAttribute("user");
        return R.success("退出登录成功~");
    }

}
