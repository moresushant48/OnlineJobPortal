package com.example.OnlineJobPlacement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.OnlineJobPlacement.Model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
