package com.services.Impl;

import com.entity.Service;
import com.entity.ServiceDTO;
import com.entity.User;
import com.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.services.Impl
 * @Project：EpidemicPreventionAndControl
 * @name：ServiceService
 * @Date：3/13/2023 6:05 PM
 * @Filename：ServiceService
 */
public interface ServiceService {
    /**
     * 获取服务列表
     */
    Result batchGetService();
    /**
     * 批量导入
     * @param serviceList
     * @return
     */
    Result batchImport(List<ServiceDTO> serviceDTOList, HttpServletRequest httpServletRequest);

    /**
     * 批量删除
     * @param serviceList
     * @return
     */
    Result batchDelete(List<ServiceDTO> serviceDTOList, HttpServletRequest httpServletRequest);

    /**
     * 批量修改
     * @param serviceList
     * @return
     */
    Result batchModify(List<ServiceDTO> serviceDTOList, HttpServletRequest httpServletRequest);

}
