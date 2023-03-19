package com.services;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.entity.Service;
import com.entity.ServiceDTO;
import com.mapper.ServiceMapper;
import com.services.Impl.ServiceService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：ServiceServiceImpl
 * @Date：3/13/2023 6:05 PM
 * @Filename：ServiceServiceImpl
 */
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Resource
    private ServiceMapper serviceMapper;

    @Resource
    Token JwtToken;

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
     * @param serviceDTOList
     * @return
     */
    @Override
    public Result batchImport(List<ServiceDTO> serviceDTOList, HttpServletRequest httpServletRequest){
        final Service[] service={null};
        if (serviceDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        serviceDTOList.forEach(serviceDTO -> {
            service[0]=BeanUtil.copyProperties(serviceDTO,Service.class);
            service[0].setUpdateBy(JwtToken.getId(httpServletRequest.getHeader("token")));
            serviceMapper.insert(service[0]);
        });
        return new Result(ResultEnum.SUCCESS,"导入成功");
    }

    /**
     * 批量删除
     *
     * @param serviceDTOList
     * @return
     */
    @Override
    public Result batchDelete(List<ServiceDTO> serviceDTOList, HttpServletRequest httpServletRequest) {
        final Service[] service={null};
        if (serviceDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        serviceDTOList.forEach(serviceDTO -> {
            UpdateWrapper<Service> updateWrapper=new UpdateWrapper();
            service[0]=BeanUtil.copyProperties(serviceDTO,Service.class);
            updateWrapper.set("status",0);
            updateWrapper.eq("id",service[0].getId());
            serviceMapper.update(null,updateWrapper);
        });
        return new Result(ResultEnum.SUCCESS,"批量删除成功");
    }

    /**
     * 批量修改
     * @param serviceDTOList
     * @return
     */
    @Override
    public Result batchUpdate(List<ServiceDTO> serviceDTOList, HttpServletRequest httpServletRequest) {
        final Service[] service={null};
        if (serviceDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        serviceDTOList.forEach(serviceDTO -> {
            service[0]= BeanUtil.copyProperties(serviceDTO,Service.class);
            service[0].setUpdateBy(JwtToken.getId(httpServletRequest.getHeader("token")));
            serviceMapper.updateById(service[0]);
        });
        return new Result(ResultEnum.SUCCESS,"批量更新成功");
    }

    /**
     * 查看某一物品信息
     *
     * @param id
     * @return
     */
    @Override
    public Result getServiceDetail(Long id) {
        return new Result(ResultEnum.SUCCESS,serviceMapper.selectById(id).toString());
    }
}
