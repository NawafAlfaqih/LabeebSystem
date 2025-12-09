package org.example.labeebsystem.Controller;


import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Model.Category;
import org.example.labeebsystem.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCategory(){
        return ResponseEntity.status(200).body(categoryService.getAllCategory());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(Integer adminId, Category category){
        categoryService.addCategory(adminId,category);
        return ResponseEntity.status(200).body(new  ApiResponse("category added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(Integer adminId,Integer id,Category category){
        categoryService.updateCategory(adminId,id,category);
        return ResponseEntity.status(200).body(new  ApiResponse("category updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(Integer adminId,Integer id){
        categoryService.deleteCategory(adminId,id);
        return ResponseEntity.status(200).body(new  ApiResponse("category deleted successfully"));

    }
}
