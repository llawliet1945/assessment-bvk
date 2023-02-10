package com.assessmentbvk.user.repositories;

import com.assessmentbvk.user.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    Optional<Discount> findByDiscountCodeAndIsdel(String discountCode, Integer isdel);

    Optional<Discount> findByDiscountUuidAndIsdel(String discountUuid, Integer isdel);

    List<Discount> findByCreatedByAndIsdel(Integer createdBy, Integer isdel);

}
