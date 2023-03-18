package pos.idm.Entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles implements Serializable{
//    enum enumRole {
//        administrator,
//        content_manager,
//        artist,
//        client
//    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @ManyToMany(mappedBy = "rolesSet")
    Set<Users> usersSet;

    public Roles(){}
    public Roles(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    //Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // To String
    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
