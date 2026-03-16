package com.abcaa.sistema_atividades.controller;

import com.abcaa.sistema_atividades.business.dto.ActivityDTO;
import com.abcaa.sistema_atividades.business.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/create")
    public ActivityDTO create(@RequestBody ActivityDTO dto){
        return activityService.create(dto);
    }

    @GetMapping("/list")
    public List<ActivityDTO> findAll(){
        return activityService.findAll();
    }

}