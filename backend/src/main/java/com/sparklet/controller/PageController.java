package com.sparklet.controller;

import com.sparklet.dto.ApiResponse;
import com.sparklet.model.PageInfo;
import com.sparklet.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pages")
public class PageController {

    @Autowired
    private PageService pageService;

    @GetMapping("/{pageId}")
    public ResponseEntity<?> getPageInfo(@PathVariable String pageId) {
        PageInfo pageInfo = pageService.getPageInfo(pageId);
        if (pageInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success(pageInfo));
    }

    @GetMapping
    public ResponseEntity<?> getAllPageInfo() {
        Map<String, PageInfo> allPages = pageService.getAllPageInfo();
        return ResponseEntity.ok(ApiResponse.success(allPages));
    }
}
