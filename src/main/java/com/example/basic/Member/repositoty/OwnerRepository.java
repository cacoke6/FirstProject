package com.example.basic.Member.repositoty;


import java.util.List;
import java.util.Optional;


import com.example.basic.Member.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnerRepository extends
        JpaRepository<Owner, Integer> {

    Owner findByIdAndName(int id ,String Name);
    Optional<Owner> findByName(String Name);
    Optional<Owner> findByNameAndPwd(String name,String pwd);

    Optional<Owner> findByEmail(String Email);

}
