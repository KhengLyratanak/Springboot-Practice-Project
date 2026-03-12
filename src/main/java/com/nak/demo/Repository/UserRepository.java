package com.nak.demo.Repository;



import com.nak.demo.Entity.Product;
import com.nak.demo.Entity.User;
import com.nak.demo.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
