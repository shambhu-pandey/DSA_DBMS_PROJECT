package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.DispatchHistory;
import com.example.ambulancedispatch.repository.DispatchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Autowired
    private DispatchHistoryRepository historyRepo;

    // ✅ always return List (array in JSON)
    @GetMapping("/history")
    public List<DispatchHistory> getHistory() {
        return historyRepo.findAll();
    }
}
