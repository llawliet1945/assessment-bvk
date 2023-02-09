package com.assessmentbvk.user.repositories;

import com.assessmentbvk.user.dto.ListItemCart;
import com.assessmentbvk.user.models.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemCartRepository extends JpaRepository<ItemCart, Integer> {

    String getListCart = "SELECT a.itms_cart_uuid as itemCartUuid, i.itms_name as itemName, i.itms_price as itemPrice," +
        " a.itms_qty as itemQty, c.cart_uuid as cartUuid, i.itms_uuid as itemUuid FROM public.itms_cart a join " +
        " public itms on itms i on a.itms_id = i.itms_id join public.cart c on c.cart_id = a.cart_id WHERE " +
        " a.created_by = :createdBy AND a.isdel = :isdel ";

    String getItemCartItemUuidAndCartUuidAndIsdel = "SELECT a.* FROM public.itms_cart a join public itms on itms i on a.itms_id = i.itms_id " +
        " join public.cart c on c.cart_id = a.cart_id WHERE i.itms_uuid = :itemUuid AND c.cart_uuid = :cartUuid AND a.isdel = :isdel ";

    @Query(nativeQuery = true, value = getListCart)
    List<ListItemCart> findByCreatedByAndIsdel(Integer createdBy, Integer isdel);

    Optional<ItemCart> findByItemIdAndCartIdAndIsdel(Integer itemId, Integer cartId, Integer isdel);

    @Query(nativeQuery = true, value = getItemCartItemUuidAndCartUuidAndIsdel)
    Optional<ItemCart> findByItemUuidAndCartUuidAndIsdel(String itemUuid, String cartUuid, Integer isdel);


}
