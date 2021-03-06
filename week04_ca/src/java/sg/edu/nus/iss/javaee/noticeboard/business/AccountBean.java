/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.javaee.noticeboard.business;

import java.sql.SQLException;
import javax.ejb.EJB;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sg.edu.nus.iss.javaee.noticeboard.model.User;

/**
 *
 * @author linby
 */
@Stateless
public class AccountBean {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private GroupBean groupBean;

    private static final String queryLogin = "SELECT u FROM User u WHERE u.userid=:userid and u.password=:password";
    
    public void register(User user) throws SQLException {
        if(user !=null){
            em.persist(user);
            groupBean.addToGroup(user, "USER");
        }
    }

    public Optional<User> findUserById(String id){
        return Optional.ofNullable(em.find(User.class, id));
     }
    
    public boolean validateLogin(User user) throws SQLException {
        TypedQuery<User> query = em.createQuery(
                queryLogin, User.class);
        query.setParameter("userid", user.getUserid());
        query.setParameter("password", user.getPassword());
        return (query.getResultList().size() == 1);
    }
    
    public void getAllUser(){
         TypedQuery<User> query = em.createQuery(
                "SELECT u from User u", User.class);
         System.out.println(query.getResultList());
    }
}
