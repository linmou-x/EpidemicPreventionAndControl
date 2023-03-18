package com.services;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.entity.Good;
import com.entity.GoodDTO;
import com.mapper.GoodMapper;
import com.services.Impl.GoodService;
import com.utils.Result;
import com.utils.ResultEnum;
import com.utils.Token;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @Resource
    Token JwtToken;

    @Override
    public Result getGoodList(){
        return new Result(ResultEnum.SUCCESS,goodMapper.getGoodList());
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
            goods[0].setUpdateBy(JwtToken.getId(httpServletRequest.getHeader("token")));
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
    public Result batchModify(List<GoodDTO> goodDTOList,HttpServletRequest httpServletRequest) {
        if (goodDTOList.isEmpty()){
            return new Result(ResultEnum.FAIL,"禁止空数组");
        }
        final Good[] goods={null};
        goodDTOList.forEach(goodDTO -> {
            goods[0]=BeanUtil.copyProperties(goodDTO,Good.class);
            UpdateWrapper<Good> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("id",goods[0].getId());
            goodMapper.update(goods[0],updateWrapper);
        });
        return new Result(ResultEnum.SUCCESS,"批量更新成功");
    }
}
