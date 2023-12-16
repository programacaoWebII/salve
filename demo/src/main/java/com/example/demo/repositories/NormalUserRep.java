package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.NormalUser;



public interface NormalUserRep extends JpaRepository<NormalUser,String>{
    NormalUser findByCpf(String cpf);
    NormalUser findByName(String name);
    List<NormalUser> findByGivenName(String given_name);
    List<NormalUser> findByFamilyName(String family_name);
}

