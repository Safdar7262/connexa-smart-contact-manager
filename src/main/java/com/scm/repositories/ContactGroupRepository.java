package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.entities.ContactGroup;

public interface ContactGroupRepository extends JpaRepository<ContactGroup, String> {

}
