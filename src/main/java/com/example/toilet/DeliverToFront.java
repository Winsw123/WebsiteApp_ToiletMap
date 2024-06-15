package com.example.toilet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://tile.openstreetmap.org") // 允许来自https://tile.openstreetmap.org的跨域请求
@RestController
public class ToiletUpdateUpload {

    @GetMapping("/Upload")
    public ArrayList<String> UploadToiletInfo() {
        ArrayList<String> jsonFormatString = new ArrayList<>();
        List<Location> toilet = new ArrayList<>();
        toilet = JdbcOperation.getAlllocations();

        for(Location i : toilet) {
            jsonFormatString.add(i.toString());
        }

        return jsonFormatString;
    }
}
