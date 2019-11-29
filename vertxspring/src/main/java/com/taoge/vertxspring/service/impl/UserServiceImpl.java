package com.taoge.vertxspring.service.impl;

import com.taoge.vertxspring.entity.User;
import com.taoge.vertxspring.mapper.UserMapper;
import com.taoge.vertxspring.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 滔哥
 * @since 2019-11-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
