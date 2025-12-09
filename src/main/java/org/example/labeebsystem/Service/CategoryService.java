package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Admin;
import org.example.labeebsystem.Model.Category;
import org.example.labeebsystem.Repository.AdminRepository;
import org.example.labeebsystem.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public void addCategory(Integer adminId,Category category){
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin==null){
            throw new ApiException("admin ID not found");
        }
        categoryRepository.save(category);
    }

    public void updateCategory(Integer adminId,Integer id,Category category){
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin==null){
            throw new ApiException("admin ID not found");
        }
        Category oldCategory=categoryRepository.findCategoriesById(id);
        if(oldCategory==null){
            throw new ApiException("category ID not found");
        }
        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
    }

    public void deleteCategory(Integer adminId,Integer id){
        Admin admin = adminRepository.findAdminById(adminId);
        if(admin==null){
            throw new ApiException("admin ID not found");
        }
        Category category=categoryRepository.findCategoriesById(id);
        if(category==null){
            throw new ApiException("category ID not found");
        }
        categoryRepository.delete(category);
    }
}
