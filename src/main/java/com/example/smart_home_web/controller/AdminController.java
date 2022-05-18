package com.example.smart_home_web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.smart_home_web.enums.*;
import com.example.smart_home_web.exception.CustomException;
import com.example.smart_home_web.pojo.*;
import com.example.smart_home_web.pojo.vo.ConnectUser;
import com.example.smart_home_web.pojo.vo.DeviceTypeTotalCount;
import com.example.smart_home_web.service.*;
import com.example.smart_home_web.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DeviceService deviceService;

    @Autowired
    ConnectService connectService;

    @Autowired
    CommandService commandService;

    @Autowired
    NotDisturbService notDisturbService;

    @Autowired
    SendMessageService sendMessageService;

    @GetMapping("index")
    public String index(Model model) {
        ControllerUtil.index(deviceService, model);
        return "admin/index";
    }

    @GetMapping("account")
    public String account(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername())
                .eq("role", RoleEnum.ADMIN));
        model.addAttribute("user", user);
        return "admin/account";
    }

    @PostMapping("updateMyAccount")
    @ResponseBody
    public String updateMyAccount(@Validated User user, String confirmPass) {
        if (!user.getPassword().equals(confirmPass)) {
            throw new CustomException("两次密码不相等，请重新输入");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUpdateTime(new Date());
        return ControllerUtil.update(userService, user);
    }

    @GetMapping("accountManage")
    public String accountManage(Model model, String searchMsg) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("role", RoleEnum.USER);
        ControllerUtil.query(userService, model, "username", searchMsg, null, queryWrapper);
        return "admin/account_manage";
    }

    @ResponseBody
    @PostMapping("removeAccount")
    public String removeAccount(String id) {
        commandService.remove(new QueryWrapper<Command>().eq("user_id", id));
        notDisturbService.remove(new QueryWrapper<NotDisturb>().eq("user_id", id));
        sendMessageService.remove(new QueryWrapper<SendMessage>().eq("user_id", id));
        connectService.remove(new QueryWrapper<Connect>().eq("user_id", id));
        return ControllerUtil.remove(userService, id);
    }

    @ResponseBody
    @PostMapping("updateAccount")
    public String updateAccount(@Validated User user, String passwordAgain) {
        if (!user.getPassword().equals(passwordAgain)) {
            throw new CustomException("两次密码不相等，请重新输入");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleEnum.USER);
        if (user.getId() == null) {
            user.setCreateTime(new Date());
        }
        user.setUpdateTime(new Date());
        return ControllerUtil.update(userService, user);
    }

    @GetMapping("deviceInfoManage")
    public String deviceInfoManage(Model model, String searchMsg, String searchTypeMsg) {
        ControllerUtil.query(deviceService, model, "name", searchMsg, searchTypeMsg, new QueryWrapper<Device>());
        return "admin/device_info_manage";
    }

    @ResponseBody
    @PostMapping("removeDeviceInfo")
    public String removeDeviceInfo(String id) {
        commandService.remove(new QueryWrapper<Command>().eq("device_id", id));
        notDisturbService.remove(new QueryWrapper<NotDisturb>().eq("device_id", id));
        sendMessageService.remove(new QueryWrapper<SendMessage>().eq("device_id", id));
        connectService.remove(new QueryWrapper<Connect>().eq("device_id", id));
        return ControllerUtil.remove(deviceService, id);
    }

    @ResponseBody
    @PostMapping("updateDeviceInfo")
    public String updateDeviceInfo(Device device, String typeMsg) {
        if ((typeMsg == null) || (typeMsg.isEmpty())) {
            throw new CustomException("设备类型不能设置为空");
        }
        device.setType(DeviceTypeEnum.forValue(typeMsg));
        return ControllerUtil.update(deviceService, device);
    }

    @GetMapping("deviceStateManage")
    public String deviceStateManage(Model model, String searchMsg, String searchTypeMsg) {
        ControllerUtil.query(deviceService, model, "name", searchMsg, searchTypeMsg, new QueryWrapper<Device>());
        return "admin/device_state_manage";
    }

    @PostMapping("changeDeviceShare")
    @ResponseBody
    public String changeDeviceShare(String id, String code) {
        Device device = new Device();
        device.setIsShared(IsSharedEnum.forValue(Integer.valueOf(code)));
        device.setId(Integer.valueOf(id));
        return ControllerUtil.update(deviceService, device);
    }

    @PostMapping("changeDeviceState")
    @ResponseBody
    public String changeDeviceState(String id, String code) {
        Device device = new Device();
        device.setState(DeviceStateEnum.forValue(Integer.valueOf(code)));
        device.setId(Integer.valueOf(id));
        return ControllerUtil.update(deviceService, device);
    }

    @GetMapping("connectManage")
    public String connectManage(Model model, String deviceId, String searchMsg) {
        List<Connect> connectList = connectService.list(new QueryWrapper<Connect>().eq("device_id", deviceId));
        List<ConnectUser> list = new ArrayList<>();
        for (Connect connect : connectList) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("id", connect.getUserId());
            if ((searchMsg != null) && (!searchMsg.isEmpty())) {
                queryWrapper = queryWrapper.like("username", searchMsg);
            }
            User user = userService.getOne(queryWrapper);
            if (user != null) {
                list.add(new ConnectUser(user, connect));
            }
        }
        model.addAttribute("deviceId", deviceId);
        ControllerUtil.addAttribute(model, searchMsg, null, list);
        return "admin/connect_manage";
    }

    @ResponseBody
    @PostMapping("sendMessage")
    public String sendMessage(SendMessage sendMessage) {
        sendMessage.setSendTime(new Date());
        return ControllerUtil.update(sendMessageService, sendMessage);
    }

}
