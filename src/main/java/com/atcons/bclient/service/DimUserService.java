package com.atcons.bclient.service;

import com.atcons.bclient.entities.DimUser;
import com.atcons.bclient.repo.DimUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Alexey on 16.08.2017 13:55.
 */
@Service
public class DimUserService {
    private final DimUserRepo dimUserRepo;

    @Autowired
    public DimUserService(DimUserRepo dimUserRepo) {
        this.dimUserRepo = dimUserRepo;
    }

    public DimUser findByUserLogin(String login) {
        return dimUserRepo.findByUserLogin(login);
    }
    public List<DimUser> findAll() {
        return dimUserRepo.findAll();
    }

}
