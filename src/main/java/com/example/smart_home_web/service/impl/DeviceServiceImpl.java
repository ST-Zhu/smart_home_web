package com.example.smart_home_web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.smart_home_web.pojo.Device;
import com.example.smart_home_web.service.DeviceService;
import com.example.smart_home_web.mapper.DeviceMapper;
import org.springframework.stereotype.Service;

/**
* @author ZST-PC
* @description 针对表【tb_device】的数据库操作Service实现
* @createDate 2022-04-23 17:20:16
*/
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device>
    implements DeviceService{

}




