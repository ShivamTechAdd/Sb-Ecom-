package com.eccomerce.project.Service;

import com.eccomerce.project.Exceptions.ApiException;
import com.eccomerce.project.Exceptions.ResourceNotFoundException;
import com.eccomerce.project.Model.Cart;
import com.eccomerce.project.Model.CartItem;
import com.eccomerce.project.Model.Product;
import com.eccomerce.project.Repository.CartItemRepository;
import com.eccomerce.project.Repository.CartRepository;
import com.eccomerce.project.Repository.ProductRepository;
import com.eccomerce.project.Util.AuthUtil;
import com.eccomerce.project.payload.CartDto;
import com.eccomerce.project.payload.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CartDto addProductToCart(Long productId, Integer quantity) {

        Cart cart = createCart();

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product", "ProductId", productId)
                );

        CartItem cartItem = cartItemRepository
                .findCartItemByProductIdAndCartId(cart.getCartId(), productId);

        if (cartItem != null)
            throw new ApiException("Product " + product.getProductName() + " already exists in the cart");

        if (product.getQuantity() == 0)
            throw new ApiException(product.getProductName() + " is not available");

        if (product.getQuantity() < quantity)
            throw new ApiException("Please order " + product.getProductName()
                    + " less than or equal to " + product.getQuantity());

        double finalPrice = product.getSpecialPrice() != null
                ? product.getSpecialPrice()
                : product.getPrice();

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(finalPrice); // ✅ never null

        cartItemRepository.save(newCartItem);

        cart.getCartItems().add(newCartItem);

        // update product stock (optional but correct)
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        // update cart total
        cart.setTotalPrice(cart.getTotalPrice() + (finalPrice * quantity));
        cartRepository.save(cart);

        // build response
        CartDto cartDto = modelMapper.map(cart, CartDto.class);

        List<CartItem> cartItems = cart.getCartItems();
        Stream<ProductDTO> productStream = cartItems.stream()
                .map(item -> {
                    ProductDTO dto = modelMapper.map(item.getProduct(), ProductDTO.class);
                    dto.setQuantity(item.getQuantity());
                    return dto;
                });

        cartDto.setProducts(productStream.toList());
        return cartDto;
    }

    @Override
    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if(carts.isEmpty()) throw new ApiException("No Carts exist.");

        List<CartDto> cartDtos = carts.stream().map(cart -> {
            CartDto cartDto = modelMapper.map(cart, CartDto.class);
            List<ProductDTO> products = cart.getCartItems().stream()
                    .map( p-> modelMapper.map(p.getProduct(),ProductDTO.class))
                    .toList();
            cartDto.setProducts(products);
            return cartDto;
        }).toList();

        return cartDtos;
    }

    private Cart createCart(){
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(userCart != null){
            return userCart;
        }
        Cart cart = new Cart();
        cart.setTotalPrice(0.00);
        cart.setUsers(authUtil.loggedInUser());
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }

}
