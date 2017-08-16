package com.atcons.bclient.repo;

import com.atcons.bclient.entities.DimUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alexey on 16.08.2017 13:50.
 */
@Repository
public interface DimUserRepo extends JpaRepository<DimUser, Long> {
    DimUser findByUserLogin(String userLogin);
}
