package com.assessmentbvk.user.repositories;

import com.assessmentbvk.user.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByItemCatId(Integer itemCatId);

    Optional<Item> findByItemUuid(String itemUuid);

}
