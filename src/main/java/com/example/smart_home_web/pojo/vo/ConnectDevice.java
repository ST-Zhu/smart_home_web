package com.example.smart_home_web.pojo.vo;

import com.example.smart_home_web.pojo.Connect;
import com.example.smart_home_web.pojo.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectDevice {

    Device device;

    Connect connect;
}
