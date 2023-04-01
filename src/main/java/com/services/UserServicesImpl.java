package com.services;

import ch.qos.logback.classic.Logger;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.PageUserDTO;
import com.entity.User;
import com.entity.UserDTO;
import com.entity.UserVO;
import com.mapper.UserMapper;
import com.services.Impl.UserService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author charles
 */
@Service
public class UserServicesImpl implements UserService {

    @Resource
    public UserMapper userMapper;

    @Resource
    Token token;

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
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        User user=userMapper.selectOne(queryWrapper);
        logger.debug(phone,password);
        if(user==null){
            return new Result(ResultEnum.FAIL,"登录失败,用户不存在");
        }else {
            if (!user.getPassword().equals(password)){
                return new Result(ResultEnum.FAIL,"登录失败,密码错误");
            }else {
                return new Result(ResultEnum.SUCCESS, token.getToken(user.getPhone(), user.getPassword(), user.getId(),user.getUserType()));
            }
        }
    }

    /**
     * 用户信息
     * @param tokenString
     */
    @Override
    public Result userinfo(String tokenString) {
        User user=new User();
        user.setUserType(token.getRole(tokenString));
        user.setName(String.valueOf(token.getId(tokenString)));
        user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return new Result(ResultEnum.SUCCESS,user);
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
                user[0].setHouseHolder(token.getId(httpServletRequest.getHeader("X-Token")));
                user[0].setUpdateBy(token.getId(httpServletRequest.getHeader("X-Token")));
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
            updateWrapper.set("update_by",token.getId(httpServletRequest.getHeader("X-Token")));
            updateWrapper.eq("id",user[0].getId());
            userMapper.update(null,updateWrapper);
        });
        return new Result(ResultEnum.SUCCESS,"批量删除成功");
    }

    @Override
    public Result batchUpdate(List<UserDTO> userDTOList,HttpServletRequest httpServletRequest) {
        final User[] user = {null};
        userDTOList.forEach(userDTO -> {
            user[0] = BeanUtil.copyProperties(userDTO,User.class);
            user[0].setUpdateBy(token.getId(httpServletRequest.getHeader("X-Token")));
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
    public Result selectByPage(PageUserDTO pageUserDTO,HttpServletRequest httpServletRequest) {
        logger.debug(pageUserDTO.toString());
        Integer currentPage=pageUserDTO.getCurrentPage();
        Integer pageSize=pageUserDTO.getPageSize();
        String token_role=token.getRole(httpServletRequest.getHeader("X-Token"));
        User user= BeanUtil.copyProperties(pageUserDTO.getUserDTO(),User.class);
        logger.debug(user.toString());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (token_role.equals("user")){
            logger.debug("用户为普通社区居民");
            queryWrapper.eq("house_holder",getHouseHolder(token.getId(httpServletRequest.getHeader("X-Token"))));
        }
        logger.debug("user_role"+token_role);
        queryWrapper.eq("del_flag",1);
        if ("admin".equals(user.getUserType())) {
            queryWrapper.eq("user_type", "admin");
        }else if ("volunteer".equals(user.getUserType())){
            queryWrapper.eq("user_type", "volunteer");
        }
        /**
         * 用户姓名非空时拼接条件到SQL语句，
         */
        queryWrapper.like(!StringUtils.isEmpty(user.getName()), "name", user.getName());
        /**
         * 条件判定非空时添加年龄查询条件
         */
        queryWrapper.eq(!"null".equals(String.valueOf(user.getAge())), "age", user.getAge());
        /**
         * 条件判定非空时添加性别查询条件
         */
        queryWrapper.eq(!StringUtils.isEmpty(String.valueOf(user.getGender())), "gender", user.getGender());
        /**
         * 条件判定非空时添加地址查询条件
         */
        queryWrapper.like(!StringUtils.isEmpty(String.valueOf(user.getAddress())), "address", user.getAddress());
        /**
         * 条件判定非空时添加手机查询条件
         */
        queryWrapper.eq(!StringUtils.isEmpty(String.valueOf(user.getPhone())), "phone", user.getPhone());
        logger.debug(userMapper.selectList(queryWrapper).toString());
        Page<User> page=new Page<>(currentPage,pageSize);
        Page<User> userPage=userMapper.selectPage(page,queryWrapper);
        return new Result(ResultEnum.SUCCESS,"this is Paging query result",userPage);
    }

    /**
     * 设置用户权限
     *
     * @param id
     */
    @Override
    public Result setAdmin(Long id) {
        User user =new User();
        user.setId(id);
        user.setUserType("admin");
        return new Result(ResultEnum.SUCCESS,"权限设置成功");
    }

    /**
     * 设置为社区工作者
     *
     * @param id
     * @return
     */
    @Override
    public Result setVolunteer(Long id) {
        User user =new User();
        user.setId(id);
        user.setUserType("volunteer");
        return new Result(ResultEnum.SUCCESS,"权限设置成功");
    }
    /**
     * 设置为用户权限
     *
     * @param id
     * @return
     */
    @Override
    public Result setUser(Long id) {
        User user =new User();
        user.setId(id);
        user.setUserType("user");
        return new Result(ResultEnum.SUCCESS,"权限设置成功");
    }

    @Override
    public List getList(Long id) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("house_holder",id);
        List<User> userList=userMapper.selectList(queryWrapper);
        List<Long> list=new ArrayList<>();
        userList.forEach(user -> {
            list.add(user.getId());
        });
        return list;
    }

    @Override
    public Long getHouseHolder(Long id) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return userMapper.selectOne(queryWrapper).getHouseHolder();
    }


}
