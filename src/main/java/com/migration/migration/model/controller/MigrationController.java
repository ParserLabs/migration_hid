package com.migration.migration.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.migration.migration.process.MigrationProcess;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/migration")
public class MigrationController {

	@Autowired
	private MigrationProcess migrationProcess;

	@GetMapping("/start")
	public ResponseEntity<Void> start() throws InterruptedException {
		migrationProcess.start();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/stop")
	public ResponseEntity<Void> stop() {
		migrationProcess.stop();
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
