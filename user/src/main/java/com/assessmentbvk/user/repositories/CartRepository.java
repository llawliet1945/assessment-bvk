package com.assessmentbvk.user.repositories;

import com.assessmentbvk.user.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByCreatedByAndIsdel(Integer createdBy, Integer isdel);

    Optional<Cart> findByCartUuidAndIsdel(String cartUuid, Integer isdel);

}
