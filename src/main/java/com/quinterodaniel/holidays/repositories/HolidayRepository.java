package com.quinterodaniel.holidays.repositories;

import com.quinterodaniel.holidays.entities.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday, Integer> {
}
