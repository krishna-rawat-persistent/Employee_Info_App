package com.psl.permission_app.controller;

import com.psl.permission_app.model.Permission;
import com.psl.permission_app.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permission")
    public Object getAllPermissions(){
        return permissionService.getAllPermission();
    }

    @GetMapping("/permission/{id}")
    public boolean getPermissionById(@PathVariable("id") String id){
        return permissionService.getPermissionById(id).isFlag();
    }

    @PostMapping("/permission")
    public void createPermission(@RequestBody Permission permission){
        permissionService.savePermission(permission);
    }

    @PutMapping("/permission")
    public void updatePermission(@RequestBody Permission permission){
        permissionService.updatePermission(permission);
    }

    @DeleteMapping("/permission")
    public void deleteAllPermission(){
        permissionService.deleteAllPermissions();
    }

    @DeleteMapping("/permission/{id}")
    public void deletePermission(@PathVariable("id") String id){
        permissionService.deletePermission(id);
    }

}
