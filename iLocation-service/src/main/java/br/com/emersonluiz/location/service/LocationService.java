package br.com.emersonluiz.location.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.emersonluiz.location.model.Location;
import br.com.emersonluiz.location.model.Status;
import br.com.emersonluiz.location.repository.LocationRepository;
import br.com.emersonluiz.location.rest.ExceptionResource;

@Named
public class LocationService extends ExceptionResource {

	@Inject
	LocationRepository locationRepository;
	
	public void create(Location location) {
		location.setLocationDate(new Date());
		locationRepository.save(location);
	}

	public void create(List<Location> locations) {
		for (Location location : locations) {
			location.setLocationDate(new Date());
			locationRepository.save(location);
		}
	}

	public List<Location> findByImei(String imei) {
		return locationRepository.findByDeviceImei(imei);
	}

	public Location findTopByImei(String imei) {
		return locationRepository.findTop1ByDeviceImei(imei);
	}
	
	public List<Location> findAllLast() {
		List<Location> location = locationRepository.findAllLast();

		location.forEach(item -> {
			Date date = item.getLocationDate();
			
			Calendar cal24 = Calendar.getInstance();
			cal24.setTime(date);
			cal24.add(Calendar.HOUR, 24);
			
			Calendar cal8 = Calendar.getInstance();
			cal8.setTime(date);
			cal8.add(Calendar.HOUR, 8);

			long last24 = cal24.getTimeInMillis();
			long last8 = cal8.getTimeInMillis();
			long now = new Date().getTime();
		
			item.setStatus(Status.Online);

			if(last8 < now) {
				item.setStatus(Status.NoTransactions);
			}

			if(last24 < now) {
				item.setStatus(Status.Offline);
			}

		});

		return location;
	}
}
