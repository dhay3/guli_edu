package com.chz.acl.controller;


import com.chz.acl.entity.Permission;
import com.chz.acl.service.PermissionService;
import com.chz.response.ResponseBo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public ResponseBo indexAllPermission() {
        List<Permission> list = permissionService.queryAllMenuGuli();
        return ResponseBo.ok().data("children", list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public ResponseBo remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return ResponseBo.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public ResponseBo doAssign(String roleId, String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipGuli(roleId, permissionId);
        return ResponseBo.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public ResponseBo toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return ResponseBo.ok().data("children", list);
    }


    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public ResponseBo save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return ResponseBo.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public ResponseBo updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return ResponseBo.ok();
    }

}

