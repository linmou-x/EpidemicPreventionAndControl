package com.services;

import com.entity.Service;
import com.mapper.ServiceMapper;
import com.services.Impl.ServiceService;
import com.utils.Result;
import com.utils.ResultEnum;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：ServiceServiceImpl
 * @Date：3/13/2023 6:05 PM
 * @Filename：ServiceServiceImpl
 */
public class ServiceServiceImpl implements ServiceService {

    @Resource
    private ServiceMapper serviceMapper;

    /**
     * 获取服务列表
     */
    @Override
    public Result batchGetService() {
        return new Result(ResultEnum.SUCCESS,serviceMapper.getServiceList());
    }

    /**
     * 批量导入
     *
     * @param serviceList
     * @return
     */
    @Override
    public Result batchImport(List<Service> serviceList) {
        serviceList.forEach(service -> {
            serviceMapper.insert(service);
        });
        return new Result(ResultEnum.SUCCESS,"导入成功");
    }

    /**
     * 批量删除
     *
     * @param serviceList
     * @return
     */
    @Override
    public Result batchDelete(List<Service> serviceList) {
        serviceList.forEach(service -> {
            serviceMapper.deleteById(service);
        });
        return new Result(ResultEnum.SUCCESS,"删除成功");
    }

    /**
     * 批量修改
     * @param serviceList
     * @return
     */
    @Override
    public Result batchModify(List<Service> serviceList) {
        serviceList.forEach(service -> {
            serviceMapper.updateById(service);
        });
        return new Result(ResultEnum.SUCCESS);
    }
}
