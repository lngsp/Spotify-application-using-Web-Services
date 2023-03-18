package pos.idm.Repository;

import pos.idm.Entity.Roles;
import pos.idm.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    @Query(value="SELECT * FROM users WHERE id=?1", nativeQuery = true)
    Users findUserById(int id);

    @Query(value="SELECT * FROM users WHERE username=?1", nativeQuery = true)
    List<Users> getUserByUsername(String username);

    @Query(value="SELECT password FROM users WHERE username=?1", nativeQuery = true)
    String getPassword(String username);

    @Query(value="SELECT * FROM users, roles WHERE users.id=?1 AND roles.user_id=?1", nativeQuery = true)
    Roles getUserRoles(int user_id);

    @Query(value = "UPDATE users SET username=?2 WHERE username=?1",nativeQuery = true)
    void updateUsername(String old_username,String new_username);

    @Query(value = "UPDATE users SET password=?2 WHERE username=?1",nativeQuery = true)
    void updatePassword(String username,String new_password);

    @Query(value = "DELETE FROM users WHERE id=?1",nativeQuery = true)
    void deleteUser(int id);
}
