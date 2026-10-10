package com.ams.academicmanagementsystem.section;

import com.ams.academicmanagementsystem.batch.Batch;
import com.ams.academicmanagementsystem.batch.BatchRepository;
import com.ams.academicmanagementsystem.batch.BatchResponse;
import com.ams.academicmanagementsystem.exception.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final BatchRepository batchRepository;

    public SectionService(SectionRepository sectionRepository, BatchRepository batchRepository) {
        this.sectionRepository = sectionRepository;
        this.batchRepository = batchRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public SectionResponse createSection(Long batchId, String name) {
        Batch batch = batchRepository.findById(batchId).orElseThrow(() -> new ResourceNotFoundException("No batch with id " + batchId));
        Section saved = sectionRepository.save(new Section(batch, name));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<SectionResponse> getAllSections() {
        return sectionRepository.findAll().stream().map(this::toResponse).toList();
    }

    private SectionResponse toResponse(Section section) {
        return SectionResponse.from(section);
    }
}
