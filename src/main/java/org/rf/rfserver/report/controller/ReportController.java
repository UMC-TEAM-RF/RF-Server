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

    /**
     * 사용자 신고
     * @param postReportReq
     * @return PostReportRes
     */
    @PostMapping("/user")
    public BaseResponse<PostReportRes> createUserReport(@RequestBody PostReportReq postReportReq) {
        try {
            return new BaseResponse<>(reportService.createUserReport(postReportReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 그룹 신고
     * @param postReportReq
     * @return PostReportRes
     */
    @PostMapping("/party")
    public BaseResponse<PostReportRes> createPartyReport(@RequestBody PostReportReq postReportReq) {
        try {
            return new BaseResponse<>(reportService.createPartyReport(postReportReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 사용자가 신고한 신고 리스트 조회
     * @param userId
     * @param pageable
     * @return PageDto[List[GetReportReporterRes]]
     */
    @GetMapping("/reporter/{userId}")
    public BaseResponse<PageDto<List<GetReportRes>>> getReporterReports(@PathVariable Long userId, Pageable pageable) {
        try {
            return new BaseResponse<>(reportService.getReporterReports(userId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 사용자가 신고 당한 신고 리스트 조회
     * @param userId
     * @param pageable
     * @return PageDto[List[GetReportActorRes]]
     */
    @GetMapping("/actor/{userId}")
    public BaseResponse<PageDto<List<GetReportRes>>> getActorReports(@PathVariable Long userId, Pageable pageable) {
        try {
            return new BaseResponse<>(reportService.getActorReports(userId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 그룹이 신고당한 신고 리스트 조회
     * @param partyId
     * @param pageable
     * @return PageDto[List[GetReportActorRes]]
     */
    @GetMapping("/actorParty/{partyId}")
    public BaseResponse<PageDto<List<GetReportRes>>> getActorPartyReports(@PathVariable Long partyId, Pageable pageable) {
        try {
            return new BaseResponse<>(reportService.getActorPartyReports(partyId, pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 전체 신고 리스트 조회
     * @param pageable
     * @return PageDto[List[GetReportRes]]
     */
    @GetMapping()
    public BaseResponse<PageDto<List<GetReportRes>>> getReports( Pageable pageable) {
        try {
            return new BaseResponse<>(reportService.getReports(pageable));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 신고 제거
     * @param reportId
     * @return DeleteReportRes
     */
    @DeleteMapping("/{reportId}")
    public BaseResponse<DeleteReportRes> deleteReport(@PathVariable Long reportId) {
        try {
            return new BaseResponse<>(reportService.deleteReport(reportId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}