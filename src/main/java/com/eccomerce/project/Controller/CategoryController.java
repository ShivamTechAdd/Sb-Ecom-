package com.eccomerce.project.Controller;

import com.eccomerce.project.Config.AppConstants;
import com.eccomerce.project.Model.Category;
import com.eccomerce.project.Service.CategoryService;
import com.eccomerce.project.payload.CategoryDTO;
import com.eccomerce.project.payload.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse>getAllCategory(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.pageNumber,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.pageSize,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_CATEGORIES_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder){

        CategoryResponse categoryResponse = categoryService.getAllCatgory(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse , HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO category){
        CategoryDTO categoryDto = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryDto,HttpStatus.CREATED);
    }

    @DeleteMapping("admin/catogories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        String status = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO category,@PathVariable Long categoryId){
        CategoryDTO updatedCategory = categoryService.updateCategory(category,categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }


}
