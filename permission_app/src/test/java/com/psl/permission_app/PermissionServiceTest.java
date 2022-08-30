package com.psl.permission_app;

import com.psl.permission_app.model.Permission;
import com.psl.permission_app.repository.PermissionRepository;
import com.psl.permission_app.service.PermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

import java.util.List;

@SpringBootTest
public class PermissionServiceTest {

    @Autowired
    PermissionService permissionService;

    @Autowired
    PermissionRepository permissionRepository;

    @Test
    void isPermissionExists(){
        List<Permission> permissions = permissionService.getAllPermission();
        assertTrue(permissions.size()>0);
    }

    @Test
    void isPermissionExistsById(){
        Permission permission = new Permission("EmployeeApp3", false);
        permissionService.savePermission(permission);
        Permission permission1 = permissionService.getPermissionById(permission.getId());
        assertTrue(permission1.getId() == permission.getId());
        assertFalse(permission1.isFlag());
    }

    @Test
    void isPermissionDeletedById(){
        Boolean beforeDeletion = permissionRepository.findById("EmployeeApp3").isPresent();
        permissionService.deletePermission("EmployeeApp3");
        Boolean afterDeletion = permissionRepository.findById("EmployeeApp3").isPresent();
        assertTrue(beforeDeletion);
        assertFalse(afterDeletion);
    }
}
