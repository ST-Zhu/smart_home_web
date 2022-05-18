package com.example.smart_home_web.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.smart_home_web.enums.*;
import com.example.smart_home_web.exception.CustomException;
import com.example.smart_home_web.pojo.*;
import com.example.smart_home_web.pojo.vo.ConnectDevice;
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
@RequestMapping("user")
public class UserController {

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
        return "user/index";
    }

    @GetMapping("account")
    public String account(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername())
                .eq("role", RoleEnum.USER));
        model.addAttribute("user", user);
        return "user/account";
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

    @GetMapping("accountDetail")
    public String accountDetail(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername())
                .eq("role", RoleEnum.USER));
        model.addAttribute("user", user);
        return "user/account_detail";
    }

    @PostMapping("updateAccountDetail")
    @ResponseBody
    public String updateAccountDetail(User user, String genderMsg) {
        user.setGender(GenderEnum.forValue(genderMsg));
        user.setUpdateTime(new Date());
        return ControllerUtil.update(userService, user);
    }

    @GetMapping("queryDeviceConnect")
    public String queryDeviceConnect(Model model, String searchMsg, String searchTypeMsg, @AuthenticationPrincipal UserDetails userDetails) {
        Integer userId = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername())).getId();
        QueryWrapper<Device> queryWrapper = new QueryWrapper<Device>()
                .and(k -> k.notInSql("id", "select device_id from tb_connect where is_deleted=0 and user_id ="+userId))
                .and(i -> i.eq("state", DeviceStateEnum.ON)
                .or(j -> j.eq("is_shared", IsSharedEnum.ON).eq("state", DeviceStateEnum.CONNECT)));
        ControllerUtil.query(deviceService, model, "name", searchMsg, searchTypeMsg, queryWrapper);
        return "user/device_connect";
    }

    @ResponseBody
    @PostMapping("connectDevice")
    public String connectDevice(String deviceId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername()));
        String result = ControllerUtil.update(connectService,
                new Connect(null, null, new Date(), user.getId(), Integer.valueOf(deviceId)));
        deviceService.update(new UpdateWrapper<Device>().set("state", DeviceStateEnum.CONNECT).eq("id", deviceId));
        return result;
    }

    @GetMapping("queryDeviceConnected")
    public String queryDeviceConnected(Model model, String searchMsg, String searchTypeMsg, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername()));
        List<Connect> connectList = connectService.list(new QueryWrapper<Connect>().eq("user_id", user.getId()));
        List<ConnectDevice> list = new ArrayList<>();
        for (Connect connect : connectList) {
            QueryWrapper<Device> queryWrapper = new QueryWrapper<Device>().eq("id", connect.getDeviceId());
            if ((searchMsg != null) && (!searchMsg.isEmpty())) {
                queryWrapper = queryWrapper.like("name", searchMsg);
            }
            if ((searchTypeMsg != null) && (!searchTypeMsg.isEmpty())) {
                queryWrapper = queryWrapper.and(i -> i.eq("type", DeviceTypeEnum.forValue(searchTypeMsg)));
            }
            Device device = deviceService.getOne(queryWrapper);
            if (device != null) {
                list.add(new ConnectDevice(device, connect));
            }
        }
        ControllerUtil.addAttribute(model, searchMsg, searchTypeMsg, list);
        return "user/device_connected";
    }

    @PostMapping("changeDeviceShare")
    @ResponseBody
    public String changeDeviceShare(String id, String code) {
        Device device = new Device();
        device.setIsShared(IsSharedEnum.forValue(Integer.valueOf(code)));
        device.setId(Integer.valueOf(id));
        return ControllerUtil.update(deviceService, device);
    }

    @ResponseBody
    @PostMapping("disconnectDevice")
    public String disconnectDevice(String connectId, String deviceId) {
        Device device = deviceService.getById(deviceId);
        if (device.getIsShared() == IsSharedEnum.OFF) {
            deviceService.update(new UpdateWrapper<Device>().set("state", DeviceStateEnum.ON)
                    .eq("id", device.getId()));
        }
        return ControllerUtil.remove(connectService, connectId);
    }

    @GetMapping("queryCommand")
    public String queryCommand(String deviceId, @AuthenticationPrincipal UserDetails userDetails, Model model, String searchMsg) {
        Integer userId = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername())).getId();
        QueryWrapper<Command> queryWrapper = new QueryWrapper<Command>().and(i -> i.eq("user_id", userId)
                .eq("device_id", deviceId));
        ControllerUtil.query(commandService, model, "message", searchMsg, null, queryWrapper);
        model.addAttribute("deviceId", deviceId);
        return "user/device_command";
    }

    @ResponseBody
    @PostMapping("removeCommand")
    public String removeCommand(String id) {
        return ControllerUtil.remove(commandService, id);
    }

    @ResponseBody
    @PostMapping("updateCommand")
    public String updateCommand(Command command, @AuthenticationPrincipal UserDetails userDetails) {
        Integer userId = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername())).getId();
        command.setCreateTime(new Date());
        command.setUpdateTime(new Date());
        command.setUserId(userId);
        return ControllerUtil.update(commandService, command);
    }

    @GetMapping("queryNotDisturb")
    public String queryNotDisturb(String deviceId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Integer userId = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername())).getId();
        QueryWrapper<NotDisturb> queryWrapper = new QueryWrapper<NotDisturb>().and(i -> i.eq("user_id", userId)
                .eq("device_id", deviceId));
        ControllerUtil.query(notDisturbService, model, null, null, null, queryWrapper);
        model.addAttribute("deviceId", deviceId);
        return "user/device_not_disturb";
    }

    @ResponseBody
    @PostMapping("removeNotDisturb")
    public String removeNotDisturb(String id) {
        return ControllerUtil.remove(notDisturbService, id);
    }

    @ResponseBody
    @PostMapping("updateNotDisturb")
    public String updateNotDisturb(NotDisturb notDisturb, @AuthenticationPrincipal UserDetails userDetails, String isEverydayMsg) {
        Integer userId = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername())).getId();
        notDisturb.setUserId(userId);
        notDisturb.setIsEveryday(NotDisturbEverydayEnum.forValue(isEverydayMsg));
        return ControllerUtil.update(notDisturbService, notDisturb);
    }

    @GetMapping("querySendMessage")
    public String querySendMessage(String deviceId, @AuthenticationPrincipal UserDetails userDetails, Model model, String searchMsg) {
        Integer userId = userService.getOne(new QueryWrapper<User>().eq("username", userDetails.getUsername())).getId();
        QueryWrapper<SendMessage> queryWrapper = new QueryWrapper<SendMessage>().and(i -> i.eq("user_id", userId)
                .eq("device_id", deviceId));
        ControllerUtil.query(sendMessageService, model, "message", searchMsg, null, queryWrapper);
        model.addAttribute("deviceId", deviceId);
        return "user/device_send_message";
    }
}
