package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.DTO_out.ParentDTOout;
import org.example.labeebsystem.Model.Parent;
import org.example.labeebsystem.Repository.AdminRepository;
import org.example.labeebsystem.Repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final AdminRepository adminRepository;

    public List<ParentDTOout> getAllParents(Integer adminId) {
        if (adminRepository.findAdminById(adminId) == null)
            throw new ApiException("Admin was not found");

        List<Parent> parentList = parentRepository.findAll();
        ArrayList<ParentDTOout> parentDTOs = new ArrayList<>();
        for (Parent p: parentList) {
            ParentDTOout dto = new ParentDTOout(
                    p.getId(),
                    p.getBalance(),
                    p.getEmail()
            );
            parentDTOs.add(dto);
        }
        return parentDTOs;
    }

    public void addParent(Parent parent) {
        parent.setBalance(0.0);
        parentRepository.save(parent);
    }

    public void updateParent(Integer parentId, Parent parent) {
        Parent oldParent = parentRepository.findParentById(parentId);
        if (oldParent == null)
            throw new ApiException("Parent was not found");

        oldParent.setEmail(parent.getEmail());
        oldParent.setPassword(parent.getPassword());

        parentRepository.save(parent);
    }

    public void deleteParent(Integer id) {
        Parent parent = parentRepository.findParentById(id);
        if (parent == null)
            throw new ApiException("Parent was not found");

        parentRepository.delete(parent);
    }
}
