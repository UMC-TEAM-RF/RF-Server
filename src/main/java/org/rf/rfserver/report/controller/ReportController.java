package org.rf.rfserver.report.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.report.dto.GetReportActorRes;
import org.rf.rfserver.report.dto.GetReportReporterRes;
import org.rf.rfserver.report.dto.PostReportReq;
import org.rf.rfserver.report.dto.PostReportRes;
import org.rf.rfserver.report.service.ReportService;
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
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @PostMapping("/party")
    public BaseResponse<PostReportRes> createPartyReport(@RequestBody PostReportReq postReportReq) {
        try {
            return new BaseResponse<>(reportService.createPartyReport(postReportReq));
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping("/reporter/{userId}")
    public BaseResponse<List<GetReportReporterRes>> getReports(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(reportService.getReports(userId));
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping("/actor/{userId}")
    public BaseResponse<List<GetReportActorRes>> getActorReports(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(reportService.getActorReports(userId));
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
