package com.quinterodaniel.holidays.repositories;

import com.quinterodaniel.holidays.entities.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends CrudRepository<Type, Integer> {
}
