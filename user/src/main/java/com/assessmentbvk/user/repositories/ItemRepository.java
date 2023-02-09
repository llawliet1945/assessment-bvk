package com.assessmentbvk.user.repositories;

import com.assessmentbvk.user.dto.ItemListDTO;
import com.assessmentbvk.user.models.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    String getListItem = "select i.itms_uuid as itemUuid, i.itms_name as itemName, i.itms_qty as itemQty, " +
            " i.itms_price as itemPrice, i.itms_img as itemImage, c.ctgory_name as categoryName " +
            " FROM public.itms i JOIN public.ctgory c ON i.itms_cat_id = c.ctgory_id " +
            " WHERE i.isdel = :isdel AND LOWER(CONCAT(i.itms_uuid, ' ', i.itms_name, ' ', i.itms_qty, ' ' " +
            " , i.itms_price, ' ', i.itms_img, ' ', c.ctgory_name)) LIKE LOWER(CONCAT('%',:search,'%'))";

    String getCountListItem = "select COUNT(i.itms_uuid) as counting FROM public.itms i JOIN public.ctgory c ON i.itms_cat_id = c.ctgory_id " +
            " WHERE i.isdel = :isdel AND LOWER(CONCAT(i.itms_uuid, ' ', i.itms_name, ' ', i.itms_qty, ' ' " +
            " , i.itms_price, ' ', i.itms_img, ' ', c.ctgory_name)) LIKE LOWER(CONCAT('%',:search,'%'))";

    String search = "LOWER(CONCAT('%',:search,'%'))";

    List<Item> findByItemCatIdAndIsdel(Integer itemCatId, Integer isdel);

    Optional<Item> findByItemUuidAndIsdel(String itemUuid, Integer isdel);

    @Query(nativeQuery = true, value = getListItem, countQuery = getCountListItem)
    Page<ItemListDTO> findByIsdel(Integer isdel, String search, Pageable pageable);

}
