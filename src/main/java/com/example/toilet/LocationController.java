package com.example.toilet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin() // 允许来自前端应用的跨域请求
public class LocationController {

    private final JdbcOperation jdbcOperation;

    @Autowired
    public LocationController(JdbcOperation jdbcOperation) {
        this.jdbcOperation = jdbcOperation;
    }

    @GetMapping("/getToiletLocation")
    public List<Location> getToiletLocations() {
        List<Location> toilets = jdbcOperation.getAllLocations();
        return toilets != null ? toilets : Collections.emptyList();
    }

    @PostMapping("/addToiletLocation")
    public void addToiletLocation(@RequestBody Location location) {
        jdbcOperation.addToiletLocation(location);
    }
}
