package pos.idm.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pos.idm.Entity.Roles;
import pos.idm.Entity.Users;
import pos.idm.Repository.RolesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolesService {

    private RolesRepository rolesRepository;

    @Autowired
    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public Users getRoleById(int id){
        return rolesRepository.findRoleById(id);
    }

    public List<Roles> getAllRoles(){
        List<Roles> list = new ArrayList<>();
        rolesRepository.findAll().forEach(l -> list.add(l));
        return list;
    }

}
