package org.example.labeebsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiResponse;
import org.example.labeebsystem.Model.Admin;
import org.example.labeebsystem.Model.Parent;
import org.example.labeebsystem.Service.ParentService;
import org.example.labeebsystem.Service.StudentPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;
    private final StudentPaymentService studentPaymentService;

    @GetMapping("/get/admin-id/{adminId}")
    public ResponseEntity<?> getAllParents(@PathVariable Integer adminId){
        return ResponseEntity.status(200).body(parentService.getAllParents(adminId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addParent(@RequestBody @Valid Parent parent){
        parentService.addParent(parent);
        return ResponseEntity.status(200).body(new ApiResponse("Parent added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateParent(@PathVariable Integer id, @RequestBody @Valid Parent parent){
        parentService.updateParent(id, parent);
        return ResponseEntity.status(200).body(new ApiResponse("Parent updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){
        parentService.deleteParent(id);
        return ResponseEntity.status(200).body(new ApiResponse("Parent delete successfully"));
    }
//الاب يضيف رصيد
    @PutMapping("/add-balance/{parentId}/{amount}")
    public ResponseEntity<?> addBalance(@PathVariable Integer parentId, @PathVariable Integer amount) {
        parentService.addBalance(parentId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Balance updated successfully"));
    }
    //توليد كود الخصم
    @GetMapping("/discount/{parentId}")
    public ResponseEntity generateDiscount(@PathVariable Integer parentId) {

        String result = parentService.generateDiscount(parentId);
        return ResponseEntity.status(200).body(new ApiResponse(result));
    }

}
