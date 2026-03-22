package com.eccomerce.project.Controller;

import com.eccomerce.project.Config.AppConstants;
import com.eccomerce.project.Service.ProductService;
import com.eccomerce.project.payload.ProductDTO;
import com.eccomerce.project.payload.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDto , @PathVariable Long categoryId){
        ProductDTO productDtoRes = productService.addProduct(productDto , categoryId);
        return new ResponseEntity<>(productDtoRes , HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<com.eccomerce.project.payload.ProductResponse> getAllProduct(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.pageNumber,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.pageSize,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY ,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR ,required = false) String sortOrder
    ){

        ProductResponse productResponse = productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductByCategory(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.pageNumber,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.pageSize,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY ,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR ,required = false) String sortOrder
        ){

        ProductResponse productResponse = productService.searchByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyWords(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.pageNumber,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.pageSize,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY ,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR ,required = false) String sortOrder
         ){

        ProductResponse productResponse = productService.searchProductByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@Valid @RequestBody ProductDTO productDtos , @PathVariable Long productId){
        ProductResponse productDto = productService.updateProduct(productDtos , productId);
        return new ResponseEntity<>(productDto , HttpStatus.OK);
    }

    @DeleteMapping("admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO productDto = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDto,HttpStatus.FOUND);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO>updateProductImage(@PathVariable Long productId , @RequestParam("image")MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId , image);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

}
