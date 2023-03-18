package pos.idm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pos.idm.Entity.Roles;
import pos.idm.Entity.Users;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    @Query(value="SELECT * FROM roles where id=?1", nativeQuery = true)
    Users findRoleById(int id);


}
