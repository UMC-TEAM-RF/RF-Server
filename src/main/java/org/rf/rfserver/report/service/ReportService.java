package org.rf.rfserver.report.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Report;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.party.PartyRepository;
import org.rf.rfserver.report.dto.GetReportActorRes;
import org.rf.rfserver.report.dto.GetReportReporterRes;
import org.rf.rfserver.report.dto.PostReportReq;
import org.rf.rfserver.report.dto.PostReportRes;
import org.rf.rfserver.report.repository.ReportRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return new PostReportRes(report.getId(), report.getActor().getNickName(), "USER");
    }

    public PostReportRes createPartyReport(PostReportReq postReportReq) throws BaseException{
        User reporter = userRepository.findById(postReportReq.getReporterId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Party actorParty = partyRepository.findById(postReportReq.getActorPartyId())
                .orElseThrow(() -> new BaseException(NO_SUCH_PARTY));
        Report report = reportRepository.save(new Report(reporter, actorParty, postReportReq.getContent()));
        return new PostReportRes(report.getId(), report.getActorParty().getName(), "PARTY");
    }

    public List<GetReportReporterRes> getReports(Long userId) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        List<Report> reports = reportRepository.findReportsByReporter(user);
        return reports.stream()
                .map(report -> report.getActor()!=null ?
                    new GetReportReporterRes(report.getActor().getId(), report.getActor().getNickName(), "USER") :
                    new GetReportReporterRes(report.getActorParty().getId(), report.getActorParty().getName(), "PARTY"))
                .collect(Collectors.toList());
    }

    public List<GetReportActorRes> getActorReports(Long userId) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        List<Report> reports = reportRepository.findReportsByActor(user);
        return reports.stream()
                .map(report -> new GetReportActorRes(report.getReporter().getId(), report.getReporter().getNickName()))
                .collect(Collectors.toList());
    }
}
