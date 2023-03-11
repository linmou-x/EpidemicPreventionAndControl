package com.services;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.entity.Good;
import com.entity.User;
import com.mapper.GoodMapper;
import com.services.Impl.GoodsService;
import com.utils.Result;
import com.utils.ResultEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodMapper goodMapper;

    /**
     * 批量导入
     *
     * @param goodList
     * @return
     */
    @Override
    public Result batchImport(List<Good> goodList) {
        goodList.forEach(good -> goodMapper.insert(good));
        return new Result(ResultEnum.SUCCESS,"批量导入成功");
    }

    /**
     * 批量删除
     *
     * @param goodList
     * @return
     */
    @Override
    public Result batchDelete(List<Good> goodList) {
        goodList.forEach(good -> {
            UpdateWrapper<Good> updateWrapper=new UpdateWrapper<>();
            updateWrapper.eq("id",good.getId());
            goodMapper.delete(updateWrapper);
        });
        return new Result(ResultEnum.SUCCESS,"批量删除成功");
    }

    /**
     * 批量修改
     *
     * @param goodList
     * @return
     */
    @Override
    public Result batchModify(List<Good> goodList) {
        goodList.forEach(good -> {
            goodMapper.updateById(good);
        });
        return new Result(ResultEnum.SUCCESS,"更新成功");
    }
}
