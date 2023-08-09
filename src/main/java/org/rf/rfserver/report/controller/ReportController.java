package org.rf.rfserver.report.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.config.PageDto;
import org.rf.rfserver.report.dto.*;
import org.rf.rfserver.report.service.ReportService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    @PostMapping("/user")
    public BaseResponse<PostReportRes> createUserReport(@RequestBody PostReportReq postReportReq) {
        try {
            return new BaseResponse<>(reportService.createUserReport(postReportReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @PostMapping("/party")
    public BaseResponse<PostReportRes> createPartyReport(@RequestBody PostReportReq postReportReq) {
        try {
            return new BaseResponse<>(reportService.createPartyReport(postReportReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping("/reporter/{userId}")
    public BaseResponse<PageDto<List<GetReportReporterRes>>> getReporterReports(@PathVariable Long userId, @PageableDefault(size=100, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return new BaseResponse<>(reportService.getReporterReports(userId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping("/actor/{userId}")
    public BaseResponse<PageDto<List<GetReportActorRes>>> getActorReports(@PathVariable Long userId, @PageableDefault(size=100, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return new BaseResponse<>(reportService.getActorReports(userId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping("/actorParty/{partyId}")
    public BaseResponse<PageDto<List<GetReportActorRes>>> getActorPartyReports(@PathVariable Long partyId, @PageableDefault(size=100, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return new BaseResponse<>(reportService.getActorPartyReports(partyId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping()
    public BaseResponse<PageDto<List<GetReportRes>>> getReports(@PageableDefault(size=100, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return new BaseResponse<>(reportService.getReports(pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @DeleteMapping("/{reportId}")
    public BaseResponse<DeleteReportRes> deleteReport(@PathVariable Long reportId) {
        try {
            return new BaseResponse<>(reportService.deleteReport(reportId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}