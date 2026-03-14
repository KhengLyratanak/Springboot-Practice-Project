package com.nak.demo.Repository;



import com.nak.demo.Entity.Product;
import com.nak.demo.Entity.User;
import com.nak.demo.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE :name IS NULL OR LOWER(u.userName) LIKE %:name% ")
    List<User> findUserWithFilters(@Param("name") String name);
}
