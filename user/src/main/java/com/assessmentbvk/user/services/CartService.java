package com.assessmentbvk.user.services;

import com.assessmentbvk.user.dto.ListItemCart;
import com.assessmentbvk.user.dto.RequestItemCart;
import com.assessmentbvk.user.dto.ResponseFinalItemCart;
import com.assessmentbvk.user.models.Cart;
import com.assessmentbvk.user.models.Discount;
import com.assessmentbvk.user.models.Item;
import com.assessmentbvk.user.models.ItemCart;
import com.assessmentbvk.user.repositories.CartRepository;
import com.assessmentbvk.user.repositories.DiscountRepository;
import com.assessmentbvk.user.repositories.ItemCartRepository;
import com.assessmentbvk.user.repositories.ItemRepository;
import com.assessmentbvk.user.utility.GenerateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCartRepository itemCartRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Transactional
    public ResponseEntity<String> addCart(Integer userId, RequestItemCart request) throws JsonProcessingException {
        Optional<Cart> checkCart = cartRepository.findByCreatedByAndIsdel(userId, 0);
        try {
            if (checkCart.isEmpty()) {
                Cart cart = new Cart();
                cart.setCartUuid(UUID.randomUUID().toString());
                cart.setIsdel(0);
                cart.setCreatedBy(userId);
                cart.setCreatedDate(new Date());
                cartRepository.save(cart);

                for (int x = 0; x < request.getItem().size(); x++ ) {
                    Optional<Item> item = itemRepository.findByItemUuidAndIsdel(request.getItem().get(x).getItemUuid(), 0);
                    if (item.isEmpty()) {
                        return GenerateResponse.notFound("Item not found", null);
                    }
                    if (item.get().getItemQty() < request.getItem().get(x).getItemQty()) {
                        return GenerateResponse.badRequest("Item not enough, Quantity Item : " + item.get().getItemQty() , null);
                    }
                    generateItemCart(checkCart.get(), item.get(), request.getItem().get(x).getItemQty(), userId);
                }
                return GenerateResponse.success("Add new cart success", null);
            } else {
                for (int x = 0; x < request.getItem().size(); x++ ) {
                    Optional<Item> item = itemRepository.findByItemUuidAndIsdel(request.getItem().get(x).getItemUuid(), 0);
                    if (item.isEmpty()) {
                        return GenerateResponse.notFound("Item not found", null);
                    }
                    if (item.get().getItemQty() < request.getItem().get(x).getItemQty()) {
                        return GenerateResponse.badRequest("Item not enough, Quantity Item : " + item.get().getItemQty() , null);
                    }
                    Optional<ItemCart> checkItem = itemCartRepository.findByItemIdAndCartIdAndIsdel(item.get().getItemId(), checkCart.get().getCartId(), 0);
                    if (checkItem.isEmpty()) {
                        generateItemCart(checkCart.get(), item.get(), request.getItem().get(x).getItemQty(), userId);
                    } else {
                        if ((checkItem.get().getItemQty() + request.getItem().get(x).getItemQty()) > item.get().getItemQty()) {
                            return GenerateResponse.badRequest("Item not enough, Quantity Item : " + item.get().getItemQty(), null);
                        }
                        checkItem.get().setItemQty(checkItem.get().getItemQty() + request.getItem().get(x).getItemQty());
                        checkItem.get().setUpdatedBy(userId);
                        checkItem.get().setUpdatedDate(new Date());
                        itemCartRepository.save(checkItem.get());
                    }
                }
                return GenerateResponse.success("Add item to cart success", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponse.error("Internal server error!", null);
        }
    }

    public ResponseEntity<String> detailCart(Integer userId) throws JsonProcessingException {
        List<ListItemCart> itemCarts = itemCartRepository.findByCreatedByAndIsdel(userId, 0);
        if (itemCarts.isEmpty()) {
            return GenerateResponse.success("Cart is empty", null);
        }
        return GenerateResponse.success("Get data success", itemCarts);
    }

    public ResponseEntity<String> deleteItemOnCart(Integer userId, String cartUuid, String itemUuid) throws JsonProcessingException {
        Optional<ItemCart> itemCart = itemCartRepository.findByItemUuidAndCartUuidAndIsdel(itemUuid, cartUuid, 0);
        if (itemCart.isEmpty()) {
            return GenerateResponse.notFound("Item not found in this cart", null);
        }
        itemCart.get().setIsdel(1);
        itemCartRepository.save(itemCart.get());
        return GenerateResponse.success("Delete item from cart success", null);
    }

    public ResponseEntity<String> reduceItemOnCart(Integer userId, String cartUuid, String itemUuid, Integer amount) throws JsonProcessingException {
        Optional<ItemCart> itemCart = itemCartRepository.findByItemUuidAndCartUuidAndIsdel(itemUuid, cartUuid, 0);
        if (itemCart.isEmpty()) {
            return GenerateResponse.notFound("Item not found in this cart", null);
        }
        if (itemCart.get().getItemQty() < amount) {
            return GenerateResponse.badRequest("Item not enough, Quantity Item : " + itemCart.get().getItemQty(), null);
        } else {
            if (itemCart.get().getItemQty() > amount) {
                itemCart.get().setItemQty(itemCart.get().getItemQty() - amount);
                itemCart.get().setUpdatedBy(userId);
                itemCart.get().setUpdatedDate(new Date());
                itemCartRepository.save(itemCart.get());
                return GenerateResponse.success("Reduce item from cart success", null);
            } else {
                itemCart.get().setIsdel(1);
                itemCartRepository.save(itemCart.get());
                return GenerateResponse.success("Delete item from cart success", null);
            }
        }
    }

    public ResponseEntity<String> calculateItemOnCart(Integer userId, RequestItemCart request, String disc) throws JsonProcessingException {
        Optional<Cart> cart = cartRepository.findByCreatedByAndIsdel(userId, 0);
        if (cart.isEmpty()) {
            return GenerateResponse.notFound("Cart not found", null);
        }
        if (request.getItem().size() < 1) {
            return GenerateResponse.badRequest("Please select item", null);
        }
        HashMap finalCart = new HashMap<>();
        Integer price = 0;
        List<ResponseFinalItemCart> items = new ArrayList<>();
        for ( int x = 0; x < request.getItem().size(); x++ ){
            ResponseFinalItemCart tempItem = new ResponseFinalItemCart();
            Optional<Item> item = itemRepository.findByItemUuidAndIsdel(request.getItem().get(x).getItemUuid(), 0);
            if (item.isEmpty()) {
                return GenerateResponse.notFound("Item not found", null);
            }
            Integer tempPrice = request.getItem().get(x).getItemQty() * item.get().getItemPrice();
            tempItem.setItemName(item.get().getItemName());
            tempItem.setItemUuid(item.get().getItemUuid());
            tempItem.setItemPrice(item.get().getItemPrice());
            tempItem.setItemQty(request.getItem().get(x).getItemQty());
            tempItem.setItemTotalPrice(tempPrice);
            items.add(tempItem);
            price += tempPrice;
        }
        if ( !disc.equals("") ) {
            Optional<Discount> discount = discountRepository.findByDiscountCodeAndIsdel(disc, 0);
            if (discount.isEmpty()) {
                return GenerateResponse.badRequest("Discount code was invalid", null);
            }
            if (discount.get().getDiscountQty() < 1) {
                return GenerateResponse.badRequest("Discount amount not enough", null);
            } else {
                finalCart.put("discountRate", discount.get().getDiscountRate()+"%");
                Integer discPrice = (price * discount.get().getDiscountRate() / 100);
                finalCart.put("discountPrice", discPrice > discount.get().getDiscountMaxRate() ? discount.get().getDiscountMaxRate() : discPrice);
                finalCart.put("summaryPrice", discPrice > discount.get().getDiscountMaxRate() ? price - discount.get().getDiscountMaxRate() : price - discPrice);
            }
        }
        finalCart.put("items", items);
        finalCart.put("totalPrices", price);
        return GenerateResponse.success("Success calculate items", finalCart);
    }

    private void generateItemCart(Cart cart, Item item, Integer itemQty, Integer userId) {
        ItemCart itemCart = new ItemCart();
        itemCart.setItemCartUuid(UUID.randomUUID().toString());
        itemCart.setCartId(cart.getCartId());
        itemCart.setItemId(item.getItemId());
        itemCart.setItemQty(itemQty);
        itemCart.setIsdel(0);
        itemCart.setCreatedBy(userId);
        itemCart.setCreatedDate(new Date());
        itemCartRepository.save(itemCart);
    }

}
