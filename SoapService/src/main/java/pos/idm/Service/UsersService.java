package pos.idm.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pos.idm.Entity.Roles;
import pos.idm.Entity.Users;
import pos.idm.Repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {


    private UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> getAllUser() {
        List<Users> list = new ArrayList<>();
        usersRepository.findAll().forEach(l -> list.add(l));
        return list;
    }

    public Users getUserById(int id){
        return usersRepository.findUserById(id);
    }

    public List<Users> getUserByUsername(String username){
        return usersRepository.getUserByUsername(username);
    }

    public boolean isUsernameInDb(String u){
        if(getUserByUsername(u) != null){
            return true;
        }
        else {
            return false;
        }
    }

    public String getPassword(String username){
        return usersRepository.getPassword(username);
    }

    public void updateUsername(String old_username,String new_username){
        usersRepository.updateUsername(old_username, new_username);
    }

    public void updatePassword(String username,String new_password){
        usersRepository.updatePassword(username, new_password);
    }

    public void deleteUser(int id){
        usersRepository.deleteUser(id);
    }

    public Roles getUserRoles(int user_id){
        return usersRepository.getUserRoles(user_id);
    }
}
