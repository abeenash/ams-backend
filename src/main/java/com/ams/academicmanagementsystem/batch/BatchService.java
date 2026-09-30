package com.ams.academicmanagementsystem.batch;

import com.ams.academicmanagementsystem.program.Program;
import com.ams.academicmanagementsystem.program.ProgramRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BatchService {
    private final BatchRepository batchRepository;
    private final ProgramRepository programRepository;

    public BatchService(BatchRepository batchRepository, ProgramRepository programRepository){
        this.batchRepository = batchRepository;
        this.programRepository = programRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public BatchResponse createBatch(Long programId, Integer intakeYear, Integer currentSemester){
        Program program = programRepository.findById(programId)
                .orElseThrow(()-> new IllegalArgumentException("No program with id "+programId));
        Batch saved = batchRepository.save(new Batch(program, intakeYear, currentSemester));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<BatchResponse> getAllBatches(){
        return batchRepository.findAll().stream().map(this::toResponse).toList();
    }

    private BatchResponse toResponse(Batch batch){
        return BatchResponse.from(batch);
    }
}
