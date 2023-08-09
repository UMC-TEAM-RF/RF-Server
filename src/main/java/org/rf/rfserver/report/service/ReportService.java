package org.rf.rfserver.report.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.PageDto;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Report;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.party.PartyRepository;
import org.rf.rfserver.report.dto.*;
import org.rf.rfserver.report.repository.ReportRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;

    public PostReportRes createUserReport(PostReportReq postReportReq) throws BaseException {
        User reporter = userRepository.findById(postReportReq.getReporterId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        User actor = userRepository.findById(postReportReq.getActorId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Report report = reportRepository.save(new Report(reporter, actor, postReportReq.getContent()));
        return new PostReportRes(report.getId(), report.getActor().getNickName(), report.getContent(), "USER");
    }

    public PostReportRes createPartyReport(PostReportReq postReportReq) throws BaseException{
        User reporter = userRepository.findById(postReportReq.getReporterId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Party actorParty = partyRepository.findById(postReportReq.getActorPartyId())
                .orElseThrow(() -> new BaseException(NO_SUCH_PARTY));
        Report report = reportRepository.save(new Report(reporter, actorParty, postReportReq.getContent()));
        return new PostReportRes(report.getId(), report.getActorParty().getName(), report.getContent(), "PARTY");
    }

    public PageDto<List<GetReportReporterRes>> getReporterReports(Long userId, Pageable pageable) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Page<Report> reports = reportRepository.findReportsByReporter(user, pageable);
        return new PageDto<>(reports.getNumber(), reports.getTotalPages()
                , reports.stream()
                .map(report -> report.getActor()!=null ?
                    new GetReportReporterRes(report.getId(), report.getActor().getId(), report.getActor().getNickName(), report.getContent(), "USER", report.getCreatedAt()) :
                    new GetReportReporterRes(report.getId(), report.getActorParty().getId(), report.getActorParty().getName(), report.getContent(), "PARTY", report.getCreatedAt()))
                .toList());
    }

    public PageDto<List<GetReportActorRes>> getActorReports(Long userId, Pageable pageable) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Page<Report> reports = reportRepository.findReportsByActor(user, pageable);
        return new PageDto<>(reports.getNumber(), reports.getTotalPages()
                , reports.stream()
                .map(report -> new GetReportActorRes(report.getId(), report.getReporter().getId(), report.getReporter().getNickName(), report.getContent(), report.getCreatedAt()))
                .toList());
    }

    public PageDto<List<GetReportActorRes>> getActorPartyReports(Long partyId, Pageable pageable) throws BaseException{
        Party actorParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(NO_SUCH_PARTY));
        Page<Report> reports = reportRepository.findReportsByActorParty(actorParty, pageable);
        return new PageDto<>(reports.getNumber(), reports.getTotalPages()
                , reports.stream()
                .map(report -> new GetReportActorRes(report.getId(), report.getReporter().getId(), report.getReporter().getNickName(), report.getContent(), report.getCreatedAt()))
                .toList());
    }

    public PageDto<List<GetReportRes>> getReports(Pageable pageable) throws BaseException{
        Page<Report> reports = reportRepository.findAll(pageable);
        return new PageDto<>(reports.getNumber(), reports.getTotalPages()
                , reports.stream()
                .map(report -> report.getActor()!=null ?
                        new GetReportRes(report.getId(), report.getReporter().getId(), report.getReporter().getNickName()
                                , report.getActor().getId(), report.getActor().getNickName(), report.getContent(), "USER", report.getCreatedAt()) :
                        new GetReportRes(report.getId(), report.getReporter().getId(), report.getReporter().getNickName()
                                , report.getActorParty().getId(), report.getActorParty().getName(), report.getContent(), "PARTY", report.getCreatedAt())
                        )
                .toList());
    }

    public DeleteReportRes deleteReport(Long reportId) throws BaseException{
        reportRepository.deleteById(reportId);
        return new DeleteReportRes(reportId);
    }
}
