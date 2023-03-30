package com.services;

import ch.qos.logback.classic.Logger;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Good;
import com.entity.GoodDTO;
import com.entity.PageGoodDTO;
import com.entity.User;
import com.mapper.GoodMapper;
import com.services.Impl.GoodService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @Author：Charles
 * @Package：com.services
 * @Project：EpidemicPreventionAndControl
 * @name：GoodsServiceImpl
 * @Date：2023/3/8 14:51
 * @Filename：GoodsServiceImpl
 */
@Service
public class GoodServiceImpl implements GoodService {
    @Resource
    private GoodMapper goodMapper;

    Logger logger = (Logger) LoggerFactory.getLogger(Logger.class);
    @Resource
    Token JwtToken;

    @Override
    public Result getGoodList(PageGoodDTO pageGoodDTO,HttpServletRequest httpServletRequest) {
        String role = JwtToken.getRole(httpServletRequest.getHeader("X-Token"));
        QueryWrapper<Good> queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", 1);
        if ("admin".equals(role) || "volunteer".equals(role)) {
            queryWrapper.or().eq("status", 0);
        }
        Integer currentPage=pageGoodDTO.getCurrentPage();
        Integer pageSize=pageGoodDTO.getPageSize();
        String token_role=JwtToken.getRole(httpServletRequest.getHeader("X-Token"));
        Good good= BeanUtil.copyProperties(pageGoodDTO.getGoodDTO(),Good.class);
        logger.debug("user_role"+token_role);
        queryWrapper.eq("del_flag",1);
        queryWrapper.like(!StringUtils.isNotBlank(good.getName()), "name", good.getName());
        queryWrapper.eq(!StringUtils.isEmpty(good.getType()),"type",good.getType());
        queryWrapper.eq(!StringUtils.isEmpty(String.valueOf(good.getId())),"id",good.getId());
        Page<Good> page=new Page<>(currentPage,pageSize);
        Page<Good> userPage=goodMapper.selectPage(page,queryWrapper);
        return new Result(ResultEnum.SUCCESS,"this is Paging query result",userPage);
    }
    /**
     * 批量导入
     *
     * @param goodDTOList
     * @return
     */
    @Override
    public Result batchImport(List<GoodDTO> goodDTOList,HttpServletRequest httpServletRequest) {
        final Good[] goods={null};
        if (goodDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        for (GoodDTO goodDTO:goodDTOList){
            goods[0]=BeanUtil.copyProperties(goodDTO,Good.class);
            goods[0].setUpdateBy(JwtToken.getId(httpServletRequest.getHeader("X-Token")));
            goodMapper.insert(goods[0]);
        }

        return new Result(ResultEnum.SUCCESS,"批量导入成功");
    }

    /**
     * 批量删除
     *
     * @param goodDTOList
     * @return
     */
    @Override
    public Result batchDelete(List<GoodDTO> goodDTOList, HttpServletRequest httpServletRequest) {
        if (goodDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        final Good[] goods={null};
        goodDTOList.forEach(goodDTO -> {
            goods[0]=BeanUtil.copyProperties(goodDTO,Good.class);
            UpdateWrapper<Good> updateWrapper=new UpdateWrapper<>();
            updateWrapper.set("status",0);
            updateWrapper.eq("id",goods[0].getId());
            goodMapper.update(null,updateWrapper);
        });
        return new Result(ResultEnum.SUCCESS,"批量删除成功");
    }

    /**
     * 批量修改
     * @param goodDTOList
     * @return
     */
    @Override
    public Result batchUpdate(List<GoodDTO> goodDTOList,HttpServletRequest httpServletRequest) {
        if (goodDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        final Good[] goods={null};
        goodDTOList.forEach(goodDTO -> {
            goods[0]=BeanUtil.copyProperties(goodDTO,Good.class);
            UpdateWrapper<Good> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("id",goods[0].getId());
            updateWrapper.eq("update_by",JwtToken.getId(httpServletRequest.getHeader("X-Token")));
            goodMapper.update(goods[0],updateWrapper);
            logger.debug("更新后的商品信息为"+getGoodDetail(goods[0].getId()).getData().toString());
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
    public Result getGoodDetail(Long id) {
        return new Result(ResultEnum.SUCCESS,goodMapper.selectById(id));
    }

    @Override
    public boolean updateGoodAmount(Long id, Integer amount) {
        logger.debug("商品修改线程启动");
        synchronized(this){
            Good good=goodMapper.selectById(id);
            logger.debug(good.toString());
            if (good.getAmount()<amount){
                logger.debug("商品数量不足");
                return false;
            }else{
                good.setAmount(good.getAmount()-amount);
                goodMapper.updateById(good);
                logger.debug("商品购买成功");
            }
        }
        return true;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Objects call() throws Exception {
        return null;
    }
}
