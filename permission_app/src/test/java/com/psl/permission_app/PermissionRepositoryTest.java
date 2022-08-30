package com.psl.permission_app;

import com.psl.permission_app.model.Permission;
import com.psl.permission_app.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

import java.util.List;

@SpringBootTest
public class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    void isPermissionsExists(){
        List<Permission> permissions = permissionRepository.findAll();
        assertTrue(permissions.size()>0);
    }

    @Test
    void isPermissionExistsById(){
        Permission permission = new Permission("EmployeeApp2",false);
        permissionRepository.save(permission);
        Permission permission1 = permissionRepository.findById("EmployeeApp2").get();
        assertTrue((permission.getId() == permission1.getId()) && (permission1.isFlag() == false));
    }

    @Test
    void isContentUpdated(){
        Permission permission = new Permission("EmployeeApp2", true);
        permissionRepository.save(permission);
        Permission permission1 = permissionRepository.findById("EmployeeApp2").get();
        assertTrue((permission.getId() == permission1.getId()) && (permission1.isFlag() == true));
    }

    @Test
    void isPermissionDeleted(){
        Boolean existsBeforeDelete = permissionRepository.findById("EmployeeApp2").isPresent();
        permissionRepository.deleteById("EmployeeApp2");
        Boolean notExistsAfterDelete = permissionRepository.findById("EmployeeApp2").isPresent();
        assertTrue(existsBeforeDelete);
        assertFalse(notExistsAfterDelete);
    }
}
