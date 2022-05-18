package com.example.smart_home_web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.smart_home_web.pojo.SendMessage;
import com.example.smart_home_web.service.SendMessageService;
import com.example.smart_home_web.mapper.SendMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author ZST-PC
* @description 针对表【tb_send_message】的数据库操作Service实现
* @createDate 2022-04-23 17:20:16
*/
@Service
public class SendMessageServiceImpl extends ServiceImpl<SendMessageMapper, SendMessage>
    implements SendMessageService{

}




