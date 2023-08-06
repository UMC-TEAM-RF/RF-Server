package org.rf.rfserver.report.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.domain.Party;
import org.rf.rfserver.domain.Report;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.party.PartyRepository;
import org.rf.rfserver.report.dto.GetReportActorRes;
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
        User repotedUser = userRepository.findById(postReportReq.getRepotedUserId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Report report = reportRepository.save(new Report(reporter, repotedUser, postReportReq.getContent()));
        return new PostReportRes(report.getId(), report.getReportedUser().getNickName());
    }

    public PostReportRes createPartyReport(PostReportReq postReportReq) throws BaseException{
        User reporter = userRepository.findById(postReportReq.getReporterId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Party repotedParty = partyRepository.findById(postReportReq.getReportedPartyId())
                .orElseThrow(() -> new BaseException(NO_SUCH_PARTY));
        Report report = reportRepository.save(new Report(reporter, repotedParty, postReportReq.getContent()));
        return new PostReportRes(report.getId(), report.getReportedParty().getName());
    }

    public List<GetReportActorRes> getReports(Long userId) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        List<Report> reports = reportRepository.findReportsByReporter(user);
        return reports.stream()
                .map(report -> report.getReportedUser()!=null ?
                    new GetReportActorRes(report.getReportedUser().getId(), report.getReportedUser().getNickName(), "user") :
                    new GetReportActorRes(report.getReportedParty().getId(), report.getReportedParty().getName(), "party"))
                .collect(Collectors.toList());
    }
}
