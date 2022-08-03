package com.psl.permission_app.service;

import com.psl.permission_app.model.Permission;
import com.psl.permission_app.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> getAllPermission(){
        return (List<Permission>) permissionRepository.findAll();
    }

    public Permission getPermissionById(String id){
        return permissionRepository.findById(id).orElse(new Permission(id,false));
    }

    public void savePermission(Permission permission){
        permissionRepository.save(permission);
    }

    public void updatePermission(Permission permission){
        permissionRepository.save(permission);
    }

    public void deletePermission(String id){
        permissionRepository.deleteById(id);
    }

    public void deleteAllPermissions(){
        permissionRepository.deleteAll();
    }
}
