package com.services.Impl;

import com.entity.User;
import com.entity.UserDTO;
import com.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    Result batchModify(List<UserDTO> userList,HttpServletRequest httpServletRequest);

}
