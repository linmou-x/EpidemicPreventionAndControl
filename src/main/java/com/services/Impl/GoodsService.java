package com.services.Impl;

import com.entity.Good;
import com.entity.User;
import com.utils.Result;

import java.util.List;

/**
 * @Author：Charles
 * @Package：com.services.Impl
 * @Project：EpidemicPreventionAndControl
 * @name：GoodsService
 * @Date：2023/3/8 14:50
 * @Filename：GoodsService
 */
public interface GoodsService {
    /**
     * 批量导入
     * @param goodList
     * @return
     */
    Result batchImport(List<Good> goodList);

    /**
     * 批量删除
     * @param goodList
     * @return
     */
    Result batchDelete(List<Good> goodList);

    /**
     * 批量修改
     * @param goodList
     * @return
     */
    Result batchModify(List<Good> goodList);

}
