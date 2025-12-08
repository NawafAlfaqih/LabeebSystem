package org.example.labeebsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.labeebsystem.API.ApiException;
import org.example.labeebsystem.Model.Admin;
import org.example.labeebsystem.Repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final EmailService emailService;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
        emailService.sendEmail(admin.getEmail(),"welcom to labeeb SYS","hello ");
    }

    public void updateAdmin(Integer id, Admin admin) {
        Admin oldAdmin = adminRepository.findAdminById(id);
        if (oldAdmin == null)
            throw new ApiException("Admin not found");

        oldAdmin.setEmail(admin.getEmail());
        oldAdmin.setPassword(admin.getPassword());
        adminRepository.save(oldAdmin);

    }

    public void deleteAdmin(Integer id) {
        Admin admin = adminRepository.findAdminById(id);
        if (admin == null)
            throw new ApiException("Admin not found");

        adminRepository.delete(admin);
    }
}
