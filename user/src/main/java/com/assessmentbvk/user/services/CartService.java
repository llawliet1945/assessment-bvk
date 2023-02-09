package com.assessmentbvk.user.services;

import com.assessmentbvk.user.dto.ListItemCart;
import com.assessmentbvk.user.dto.RequestItemCart;
import com.assessmentbvk.user.models.Cart;
import com.assessmentbvk.user.models.Item;
import com.assessmentbvk.user.models.ItemCart;
import com.assessmentbvk.user.repositories.CartRepository;
import com.assessmentbvk.user.repositories.ItemCartRepository;
import com.assessmentbvk.user.repositories.ItemRepository;
import com.assessmentbvk.user.utility.GenerateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCartRepository itemCartRepository;

    @Transactional
    public ResponseEntity<String> addCart(Integer userId, RequestItemCart request) throws JsonProcessingException {
        Optional<Cart> checkCart = cartRepository.findByCreatedByAndIsdel(userId, 0);
        try {
            if (checkCart.isEmpty()) {
                Cart cart = new Cart();
                cart.setCartUuid(UUID.randomUUID().toString());
                cart.setCreatedBy(userId);
                cart.setCreatedDate(new Date());
                cartRepository.save(cart);

                for (int x = 0; x < request.getItem().size(); x++ ) {
                    ItemCart itemCart = new ItemCart();
                    itemCart.setCartId(cart.getCartId());
                    Optional<Item> item = itemRepository.findByItemUuidAndIsdel(request.getItem().get(x).getItemUuid(), 0);
                    if (item.isEmpty()) {
                        return GenerateResponse.notFound("Item not found", null);
                    }
                    if (item.get().getItemQty() < request.getItem().get(x).getItemQty()) {
                        return GenerateResponse.notFound("Item not enough, Quantity Item : " + item.get().getItemQty() , null);
                    }
                    itemCart.setItemId(item.get().getItemId());
                    itemCart.setItemQty(request.getItem().get(x).getItemQty());
                    itemCartRepository.save(itemCart);
                }
                return GenerateResponse.success("Add new cart success", null);
            } else {
                for (int x = 0; x < request.getItem().size(); x++ ) {
                    Optional<Item> item = itemRepository.findByItemUuidAndIsdel(request.getItem().get(x).getItemUuid(), 0);
                    if (item.isEmpty()) {
                        return GenerateResponse.notFound("Item not found", null);
                    }
                    if (item.get().getItemQty() < request.getItem().get(x).getItemQty()) {
                        return GenerateResponse.notFound("Item not enough, Quantity Item : " + item.get().getItemQty() , null);
                    }
                    Optional<ItemCart> checkItem = itemCartRepository.findByItemIdAndCartIdAndIsdel(item.get().getItemId(), checkCart.get().getCartId(), 0);
                    if (checkItem.isEmpty()) {
                        ItemCart itemCart = new ItemCart();
                        itemCart.setCartId(checkCart.get().getCartId());
                        itemCart.setItemId(item.get().getItemId());
                        itemCart.setItemQty(request.getItem().get(x).getItemQty());
                        itemCartRepository.save(itemCart);
                    } else {
                        if ((checkItem.get().getItemQty() + request.getItem().get(x).getItemQty()) > item.get().getItemQty()) {
                            return GenerateResponse.notFound("Item not enough, Quantity Item : " + item.get().getItemQty() , null);
                        }
                        checkItem.get().setItemQty(checkItem.get().getItemQty() + request.getItem().get(x).getItemQty());
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

}
