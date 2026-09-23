package com.ams.academicmanagementsystem.batch;

import com.ams.academicmanagementsystem.program.ProgramController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
public class BatchController {
    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public List<BatchResponse> getAllBatches() {
        return batchService.getAllBatches();
    }

    @PostMapping
    public BatchResponse createBatch(@RequestBody CreateBatchRequest request) {
        return batchService.createBatch(request.programId(),
                request.intakeYear(),
                request.currentSemester());
    }

    public record CreateBatchRequest(Long programId, Integer intakeYear, Integer currentSemester) {
    }
}
