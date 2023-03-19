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

    /**
     * 分页查询
     * @param
     * @return
     */
    Result selectByPage(@RequestBody PageUserDTO pageUserDTO);
}
