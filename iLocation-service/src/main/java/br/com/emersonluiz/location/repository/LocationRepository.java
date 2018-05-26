package br.com.emersonluiz.location.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.emersonluiz.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	List<Location> findByDeviceImei(String imei);

	Location findTop1ByDeviceImei(String imei);
	
	@Query("SELECT loc FROM Location loc WHERE loc.id IN (SELECT Max(l.id) FROM Location l JOIN l.device d GROUP BY d.id)")
	List<Location> findAllLast();
}
