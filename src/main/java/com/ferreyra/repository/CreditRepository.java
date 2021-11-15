package com.ferreyra.repository;

import com.ferreyra.model.Credit;
import com.ferreyra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Integer> {

    @Query(value = "select * from credits c " +
            "join users u\n" +
            "\ton c.fk_id_user = u.id_user\n" +
            "where u.id_user= :id_user", nativeQuery = true)
    List<Credit> getCreditsByUser(@Param("id_user") Integer idUser);


    @Query(value = "call sp_add_credit(?1, ?2, ?3, ?4)", nativeQuery = true)
    public Integer addCredit(Integer userId, Double price, Boolean active, String hashCode);

    @Transactional
    @Modifying
    @Query(value = "UPDATE credits SET active = false WHERE id_credit = ?1", nativeQuery = true)
    public void updateCredit(Integer creditId);



}
