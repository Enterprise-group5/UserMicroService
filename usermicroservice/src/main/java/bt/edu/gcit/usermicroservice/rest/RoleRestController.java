package bt.edu.gcit.usermicroservice.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bt.edu.gcit.usermicroservice.entity.Role;
import bt.edu.gcit.usermicroservice.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleRestController {
    private RoleService roleService;

    @Autowired
    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public void addRole(@RequestBody Role role) {
        roleService.addRole(role);
    }

    @GetMapping("/allroles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles(); 
        return ResponseEntity.ok(roles);
    }
}