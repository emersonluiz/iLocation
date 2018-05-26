package br.com.emersonluiz.location.rest;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.emersonluiz.location.model.Location;
import br.com.emersonluiz.location.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationResource extends ExceptionResource {

	private Logger logger = LoggerFactory.getLogger(LocationResource.class);

	@Inject
	LocationService locationService;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<Location> create(@RequestBody @Valid Location location, HttpServletRequest request) throws Exception {
		logger.debug("Location create started");
		locationService.create(location);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(location.getId()).toUri();
		return ResponseEntity.created(uri).body(location);
	}

	@RequestMapping(value="/list", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<List<Location>> create(@RequestBody @Valid List<Location> locations, HttpServletRequest request) throws Exception {
		logger.debug("Location create started");
		locationService.create(locations);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("various").toUri();
		return ResponseEntity.created(uri).body(locations);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{imei}", method = RequestMethod.GET, produces = { "application/json" })
	public List<Location> findByImei(@PathVariable("imei") String imei) throws Exception {
		logger.debug("Location by imei started");
		return locationService.findByImei(imei);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/last/{imei}", method = RequestMethod.GET, produces = { "application/json" })
	public Location findTopByImei(@PathVariable("imei") String imei) throws Exception {
		logger.debug("Location by imei started");
		return locationService.findTopByImei(imei);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET, produces = { "application/json" })
	public List<Location> findAllLast() throws Exception {
		logger.debug("Location by imei started");
		return locationService.findAllLast();
	}
}
