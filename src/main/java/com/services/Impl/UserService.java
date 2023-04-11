package com.services.Impl;

import com.entity.PageUserDTO;
import com.entity.User;
import com.entity.UserDTO;
import com.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    User getUserByPhone(String phone);


    String getPasswordByPhone(String  phone);
    /**
     * 登陆
     * @param phone
     * @param password
     * @return
     */
    Result login(String phone,String password);

    /**
     * 用户信息
     */
    Result userinfo(String token);
    /**
     * 批量导入
     * @param userList
     * @return
     */
    Result batchImport(List<UserDTO> userList, HttpServletRequest httpServletRequest);

    /**
     * 批量删除
     * @param userList
     * @return
     */
    Result batchDelete(List<UserDTO> userList,HttpServletRequest httpServletRequest);

    /**
     * 批量修改
     * @param userList
     * @return
     */
    Result batchUpdate(List<UserDTO> userList,HttpServletRequest httpServletRequest);

    /**
     * 分页查询
     * @param
     * @return
     */
    Result selectByPage(PageUserDTO pageUserDTO,HttpServletRequest httpServletRequest);


    /**
     * 设置用户为管理员权限
     */
    Result setAdmin(Long id);

    /**
     * 设置为社区工作者
     * @param id
     * @return
     */

    Result setVolunteer(Long id);

    /**
     * 设置为用户权限
     * @param id
     * @return
     */
    Result setUser(Long id);

    List getList(Long id);

    Long getHouseHolder(Long id);

    Result selectFamilyByPage(PageUserDTO pageUserDTO,HttpServletRequest httpServletRequest);


    Result updatePassword(Long id,String oldPwd,String newPwd,HttpServletRequest httpServletRequest);
}
