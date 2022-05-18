package com.example.smart_home_web.pojo.vo;

import com.example.smart_home_web.pojo.Connect;
import com.example.smart_home_web.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectUser {

    User user;

    Connect connect;

}
