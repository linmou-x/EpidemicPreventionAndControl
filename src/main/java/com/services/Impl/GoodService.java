package com.services.Impl;

import com.entity.Good;
import com.entity.GoodDTO;
import com.entity.PageGoodDTO;
import com.utils.Result;
import lombok.Synchronized;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @Author：Charles
 * @Package：com.services.Impl
 * @Project：EpidemicPreventionAndControl
 * @name：GoodsService
 * @Date：2023/3/8 14:50
 * @Filename：GoodsService
 */
public interface GoodService extends Callable<Objects> {
    Result getGoodList(PageGoodDTO pageGoodDTO,HttpServletRequest httpServletRequest);
    /**
     * 批量导入
     * @param  goodDTOList
     * @return
     */
    Result batchImport(List<GoodDTO> goodDTOList,HttpServletRequest httpServletRequest);

    /**
     * 批量删除
     * @param  goodDTOList
     * @return
     */
    Result batchDelete(List<GoodDTO> goodDTOList, HttpServletRequest httpServletRequest);

    /**
     * 批量修改
     * @param  goodDTOList
     * @return
     */
    Result batchUpdate(List<GoodDTO> goodDTOList,HttpServletRequest httpServletRequest);

    /**
     * 查看某一物品信息
     * @param id
     * @return
     */
    Result getGoodDetail(Long id);

    boolean updateGoodAmount(Long id, Integer amount);
}
