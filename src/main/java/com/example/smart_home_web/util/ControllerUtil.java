package com.example.smart_home_web.util;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.smart_home_web.enums.DeviceTypeEnum;
import com.example.smart_home_web.enums.PromptEnum;
import com.example.smart_home_web.exception.CustomException;
import com.example.smart_home_web.pojo.Device;
import com.example.smart_home_web.pojo.vo.DeviceTypeTotalCount;
import com.example.smart_home_web.result.Result;
import org.springframework.ui.Model;

import java.util.List;

public class ControllerUtil {

    public static <S> void addAttribute(Model model, String searchMsg, String searchTypeMsg, List<S> list) {
        model.addAttribute("list", list);
        model.addAttribute("searchMsg", searchMsg);
        model.addAttribute("searchTypeMsg", searchTypeMsg);
        if (list.size() == 0) {
            model.addAttribute("prompt", PromptEnum.PROMPT_NONE);
        } else {
            model.addAttribute("prompt", PromptEnum.PROMPT_SOME);
        }
    }

    public static <S> void query(IService<S> service, Model model, String column, String searchMsg, String searchTypeMsg, QueryWrapper<S> queryWrapper) {
        List<S> list;
        if ((searchMsg != null) && (!searchMsg.isEmpty())) {
            queryWrapper = queryWrapper.and(i -> i.like(column, searchMsg));
        }
        if ((searchTypeMsg != null) && (!searchTypeMsg.isEmpty())) {
            queryWrapper = queryWrapper.and(i -> i.eq("type", DeviceTypeEnum.forValue(searchTypeMsg)));
        }
        list = service.list(queryWrapper);
        ControllerUtil.addAttribute(model, searchMsg, searchTypeMsg, list);
    }


    public static <S> String remove(IService<S> service, String id) {
        if ((id == null) || (id.isEmpty())) {
            throw new CustomException("删除信息不能为空");
        }
        boolean b = service.removeById(id);
        if (!b) {
            throw new CustomException(PromptEnum.PROMPT_FAIL.getMessage());
        }
        return JSON.toJSONString(Result.buildSuccess(PromptEnum.PROMPT_SUCCESS.getMessage()));
    }

    public static <S> String update(IService<S> service, S pojo) {
        boolean b = service.saveOrUpdate(pojo);
        if (!b) {
            throw new CustomException(PromptEnum.PROMPT_FAIL.getMessage());
        }
        return JSON.toJSONString(Result.buildSuccess(PromptEnum.PROMPT_SUCCESS.getMessage()));
    }

    public static <S> void index(IService<S> service, Model model) {
        DeviceTypeTotalCount deviceTypeTotalCount = new DeviceTypeTotalCount();
        deviceTypeTotalCount.setOther(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.OTHER)));
        deviceTypeTotalCount.setAudio(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.AUDIO)));
        deviceTypeTotalCount.setLight(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.LIGHT)));
        deviceTypeTotalCount.setCurtain(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.CURTAIN)));
        deviceTypeTotalCount.setDoor(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.DOOR)));
        deviceTypeTotalCount.setTv(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.TV)));
        deviceTypeTotalCount.setAir(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.AIR)));
        deviceTypeTotalCount.setHeater(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.HEATER)));
        deviceTypeTotalCount.setKitchen(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.KITCHEN)));
        deviceTypeTotalCount.setRobot(service.count(new QueryWrapper<S>().eq("type", DeviceTypeEnum.ROBOT)));
        model.addAttribute("deviceTypeTotalCount", deviceTypeTotalCount);
    }
}
