package com.ams.academicmanagementsystem.section;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {
    private final SectionService sectionService;

    public SectionController(SectionService sectionService){
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionResponse> getAllSections(){
        return sectionService.getAllSections();
    }

    @PostMapping
    public SectionResponse createSection(@RequestBody CreateSectionRequest request){
        return sectionService.createSection(request.batchId(), request.name());
    }

    public record CreateSectionRequest(Long batchId, String name){}
}
