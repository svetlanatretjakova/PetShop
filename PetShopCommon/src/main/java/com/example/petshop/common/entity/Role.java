package com.example.petshop.common.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name= "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Using GenerationType.AUTO lets
    // Hibernate decides whichstrategy to be used depending on which database.
    // The GenerationType. IDENTITY tells Hibernate consider it as identity column.
    private Integer id;
    @Column(length = 40, nullable = false, unique = true)
    private String name;
    @Column(length = 150, nullable = false)
    private String description;

    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //the false won't be returned if a Role instance is passed to the equals method.
    // But if it is not a Person instance then the getClass() != obj.getClass()
    // will be true and the equals will exit with false as the return value.
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass()) //The obj.getClass() will return the runtime class of the object,
            // that is it will check at runtime what is the Object reference is pointing to.
            return false;
        Role other = (Role) obj;
        if(id == null){
            if(other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
//It is to ensure that a user won't be assigned a role twice,
// and also to make the edit/update user function in the view with Thymeleaf working correctly.
    @Override
    public int hashCode() { //unique id
        return Objects.hash(id);
    }
}
