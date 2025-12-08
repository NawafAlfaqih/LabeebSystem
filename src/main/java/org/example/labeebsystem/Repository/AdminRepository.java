package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findAdminById(Integer id);
}
