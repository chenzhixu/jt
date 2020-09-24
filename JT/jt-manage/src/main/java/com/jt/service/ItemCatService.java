package com.jt.service;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

import java.util.List;

public interface ItemCatService {

    String findItemCatNameById(Long itemCatId);


   List<EasyUITree> findItemCatListByParentId(Long parentId);

    List<EasyUITree> findItemCache(Long parentId);
}
