package com.assessmentbvk.user.services;

import com.assessmentbvk.user.dto.DiscountDetail;
import com.assessmentbvk.user.dto.RequestDiscountDTO;
import com.assessmentbvk.user.models.Discount;
import com.assessmentbvk.user.repositories.DiscountRepository;
import com.assessmentbvk.user.utility.GenerateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<String> addDiscount(Integer userId, RequestDiscountDTO request) throws JsonProcessingException {
        try {
            Optional<Discount> checkDiscountCode = discountRepository.findByDiscountCodeAndIsdel(request.getDiscountCode(), 0);
            if (checkDiscountCode.isPresent()) {
                return GenerateResponse.badRequest("Discount code was already taken", null);
            }
            Discount discount = mapper.map(request, Discount.class);
            discount.setDiscountUuid(UUID.randomUUID().toString());
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DAY_OF_WEEK, request.getDiscountExpDate());
            discount.setDiscountExpDate(calendar.getTime());
            discount.setCreatedBy(userId);
            discount.setCreatedDate(now);
            discount.setIsdel(0);
            discountRepository.save(discount);
            return GenerateResponse.success("Success add new discount", null);
        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponse.error("Internal server error", null);
        }
    }

    public ResponseEntity<String> updateDiscount(Integer userId, String discountUuid, RequestDiscountDTO request) throws JsonProcessingException {
        Optional<Discount> checkDiscount = discountRepository.findByDiscountUuidAndIsdel(discountUuid, 0);
        if (checkDiscount.isEmpty()) {
            return GenerateResponse.notFound("Discount not found", null);
        }
        checkDiscount.get().setDiscountCode(request.getDiscountCode());
        checkDiscount.get().setDiscountDesc(request.getDiscountDesc());
        checkDiscount.get().setDiscountQty(request.getDiscountQty());
        checkDiscount.get().setDiscountRate(request.getDiscountRate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkDiscount.get().getCreatedDate());
        calendar.add(Calendar.DAY_OF_WEEK, request.getDiscountExpDate());
        checkDiscount.get().setDiscountExpDate(calendar.getTime());
        checkDiscount.get().setDiscountMinPrice(request.getDiscountMinPrice());
        checkDiscount.get().setDiscountMaxRate(request.getDiscountMaxRate());
        checkDiscount.get().setUpdatedBy(userId);
        checkDiscount.get().setUpdatedDate(new Date());
        discountRepository.save(checkDiscount.get());
        return GenerateResponse.success("Success update discount", null);
    }

    public ResponseEntity<String> listDiscount(Integer userId) throws JsonProcessingException {
        List<Discount> discount = discountRepository.findByCreatedByAndIsdel(userId, 0);
        if (discount.isEmpty()){
            return GenerateResponse.notFound("Discount not found", null);
        }
        List<DiscountDetail> detail = discount.stream().map(x -> mapper.map(x, DiscountDetail.class)).collect(Collectors.toList());
        return GenerateResponse.success("Get data success", detail);
    }

    public ResponseEntity<String> detailDiscount(Integer userId, String discountUuid) throws JsonProcessingException {
        Optional<Discount> checkDiscount = discountRepository.findByDiscountUuidAndIsdel(discountUuid, 0);
        if (checkDiscount.isEmpty()) {
            return GenerateResponse.notFound("Discount not found", null);
        }
        DiscountDetail detail = mapper.map(checkDiscount.get(), DiscountDetail.class);
        return GenerateResponse.success("Get data success", detail);
    }

    public ResponseEntity<String> deleteDiscount(String discountUuid) throws JsonProcessingException {
        Optional<Discount> discount = discountRepository.findByDiscountUuidAndIsdel(discountUuid, 0);
        if (discount.isEmpty()) {
            return GenerateResponse.notFound("Discount not found", null);
        }
        discount.get().setIsdel(1);
        return GenerateResponse.success("Delete discount success", null);
    }

}
