package com.services;

import ch.qos.logback.classic.Logger;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.entity.PageUserDTO;
import com.entity.User;
import com.entity.UserDTO;
import com.entity.UserVO;
import com.mapper.UserMapper;
import com.services.Impl.UserService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：UserServices
 * @Date：2023/1/2 20:48
 * @Filename：UserServices
 */

@Service
public class UserServicesImpl implements UserService {

    @Resource
    public UserMapper userMapper;

    @Resource
    Token JWTtoken;

    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public String getPasswordByPhone(String phone) {
        return userMapper.getPasswordByPhone(phone);
    }

    @Override
    public Result login(String phone, String password) {
        User user=userMapper.getUserByPhone(phone);
        if(user==null){
            return new Result(ResultEnum.FAIL,"登录失败,用户不存在");
        }else {
            if (!user.getPassword().equals(password)){
                return new Result(ResultEnum.FAIL,"登录失败,密码错误");
            }else {
                String token = JWTtoken.getToken(user.getPhone(), user.getPassword(), user.getId(),user.getUserType());
                return new Result(ResultEnum.SUCCESS, token);
            }
        }
    }

    @Override
    public Result batchImport(List<UserDTO> userDTOList, HttpServletRequest httpServletRequest) {
        final User[] user = {null};
        final UserVO[] userVOS = {null};
        if (userDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        List<User> userList=new ArrayList<>(userDTOList.size());
        for (UserDTO userDTO:userDTOList){
            user[0]=BeanUtil.copyProperties(userDTO,User.class);
            /**
             * 查询数据库是否有相同手机号
             */
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("phone",user[0].getPhone());
            logger.debug(String.valueOf(userMapper.selectList(queryWrapper).isEmpty()));
            if (userMapper.selectList(queryWrapper).isEmpty()){
                user[0].setPassword(user[0].getPhone());
                user[0].setUpdateBy(JWTtoken.getId(httpServletRequest.getHeader("token")));
                userMapper.insert(user[0]);
            }else {
                userList.add(user[0]);
                break;
            }
        }
        if (userList.isEmpty()){
            return new Result(ResultEnum.SUCCESS,"批量导入成功");
        }else {
            List<UserVO> returnList =new ArrayList<>();
            for (User user1:userList){
                userVOS[0]=BeanUtil.copyProperties(user1,UserVO.class);
                returnList.add(userVOS[0]);
            }
            return new Result(ResultEnum.FAIL,"部分用户数据导入失败",returnList);
        }

    }

    @Override
    public Result batchDelete(List<UserDTO> userDTOList,HttpServletRequest httpServletRequest) {
        final User[] user = {null};
        if (userDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        userDTOList.forEach(userDTO -> {
            /**
             * updawrapper 在循环外创建时语句为UPDATE user SET del_flag=？, del_flag=？WHERE (id =?AND id=?)
             * 只能更新最开始的一条
             */
            user[0] = BeanUtil.copyProperties(userDTO,User.class);
            UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
            updateWrapper.set("del_flag",0);
            updateWrapper.set("update_by",JWTtoken.getId(httpServletRequest.getHeader("token")));
            updateWrapper.eq("id",user[0].getId());
            userMapper.update(null,updateWrapper);
        });
        return new Result(ResultEnum.SUCCESS,"批量删除成功");
    }

    @Override
    public Result batchModify(List<UserDTO> userDTOList,HttpServletRequest httpServletRequest) {
        final User[] user = {null};
        userDTOList.forEach(userDTO -> {
            user[0] = BeanUtil.copyProperties(userDTO,User.class);
            user[0].setUpdateBy(JWTtoken.getId(httpServletRequest.getHeader("token")));
            userMapper.updateById(user[0]);
        });
        return new Result(ResultEnum.SUCCESS,"更新成功");
    }

    /**
     * 分页查询
     *
     * @param pageUserDTO@return
     */
    @Override
    public Result selectByPage(PageUserDTO pageUserDTO) {

        return null;
    }

}
