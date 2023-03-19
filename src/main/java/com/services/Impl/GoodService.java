package com.services.Impl;

import com.entity.Good;
import com.entity.GoodDTO;
import com.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author：Charles
 * @Package：com.services.Impl
 * @Project：EpidemicPreventionAndControl
 * @name：GoodsService
 * @Date：2023/3/8 14:50
 * @Filename：GoodsService
 */
public interface GoodService {
    Result getGoodList();
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
}
