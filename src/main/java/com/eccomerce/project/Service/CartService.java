package com.eccomerce.project.Service;

import com.eccomerce.project.payload.CartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    CartDto addProductToCart(Long productId, Integer quantity);
    List<CartDto> getAllCarts();
}
