package com.saxnart.Saxnart.repository;

import com.saxnart.Saxnart.entity.RoleEntity;
import com.saxnart.Saxnart.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RoleEntity> getRole(UserEntity user){
        StringBuilder sql = new StringBuilder().append("SELECT saxnart.role.name as name FROM saxnart.user join saxnart.user_role on saxnart.user.id = saxnart.user_role.users_id join saxnart.role on saxnart.role.id = saxnart.user_role.roles_id");
        //StringBuilder sql = new StringBuilder().append("SELECT sax03147_localhost.role.name as name FROM sax03147_localhost.user join sax03147_localhost.user_role on sax03147_localhost.user.id = sax03147_localhost.user_role.users_id join sax03147_localhost.role on sax03147_localhost.role.id = sax03147_localhost.user_role.roles_id");

        sql.append(" Where 1=1");
        if(user.getUsername() != null){
            sql.append(" and username = :username");
        }
        NativeQuery<RoleEntity> query = ((Session) entityManager.getDelegate()) .createNativeQuery(sql.toString());

        if(user.getUsername() != null){
            query.setParameter("username", user.getUsername());
        }
        query.addScalar("name", StandardBasicTypes.STRING);
        query.setResultTransformer(Transformers.aliasToBean(RoleEntity.class));
        return query.list();
    }
}
