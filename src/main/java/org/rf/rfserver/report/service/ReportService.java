package org.rf.rfserver.report.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.chat.repository.ChatRepository;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.PageDto;
import org.rf.rfserver.domain.*;
import org.rf.rfserver.party.repository.PartyRepository;
import org.rf.rfserver.report.dto.*;
import org.rf.rfserver.report.repository.ReportRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final ChatRepository chatRepository;

    public PostReportRes createUserReport(PostReportReq postReportReq) throws BaseException {
        User reporter = userRepository.findById(postReportReq.getReporterId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        User reportedUser = userRepository.findById(postReportReq.getReportedUserId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Report report = reportRepository.save(new Report(reporter, reportedUser, postReportReq.getContent(), postReportReq.getReportType()));
        reportedUser.increaseReport();
        return new PostReportRes(report);
    }

    public PostReportRes createPartyReport(PostReportReq postReportReq) throws BaseException{
        User reporter = userRepository.findById(postReportReq.getReporterId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Party reportedParty = partyRepository.findById(postReportReq.getReportedPartyId())
                .orElseThrow(() -> new BaseException(NO_SUCH_PARTY));
        Report report = reportRepository.save(new Report(reporter, reportedParty, postReportReq.getContent(), postReportReq.getReportType()));
        return new PostReportRes(report);
    }

    public PostReportRes createChatReport(PostReportReq postReportReq) throws BaseException{
        User reporter = userRepository.findById(postReportReq.getReporterId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        User reportedUser = userRepository.findById(postReportReq.getReportedUserId())
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Party reportedParty = partyRepository.findById(postReportReq.getReportedPartyId())
                .orElseThrow(() -> new BaseException(NO_SUCH_PARTY));
        Chat reportedChat = chatRepository.findById(postReportReq.getReportedChatId())
                .orElseThrow(() -> new BaseException(NO_SUCH_CHAT));
        Report report = reportRepository.save(new Report(reporter, reportedUser, reportedParty, reportedChat, postReportReq.getContent(), postReportReq.getReportType()));
        return new PostReportRes(report);
    }

    public PageDto<List<GetReportRes>> getReporterReports(Long userId, Pageable pageable) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Page<Report> reports = reportRepository.findReportsByReporter(user, pageable);
        return new PageDto<>(reports.getNumber(), reports.getTotalPages()
                , reports.stream()
                .map(report -> new GetReportRes(report))
                .toList());
    }

    public PageDto<List<GetReportRes>> getReportedUserReports(Long userId, Pageable pageable) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NO_SUCH_USER));
        Page<Report> reports = reportRepository.findReportsByReportedUser(user, pageable);
        return new PageDto<>(reports.getNumber(), reports.getTotalPages()
                , reports.stream()
                .map(report -> new GetReportRes(report))
                .toList());
    }

    public PageDto<List<GetReportRes>> getReportedPartyReports(Long partyId, Pageable pageable) throws BaseException{
        Party reportedParty = partyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(NO_SUCH_PARTY));
        Page<Report> reports = reportRepository.findReportsByReportedParty(reportedParty, pageable);
        return new PageDto<>(reports.getNumber(), reports.getTotalPages()
                , reports.stream()
                .map(report -> new GetReportRes(report))
                .toList());
    }

    public PageDto<List<GetReportRes>> getReports(Pageable pageable) throws BaseException{
        Page<Report> reports = reportRepository.findAll(pageable);
        return new PageDto<>(reports.getNumber(), reports.getTotalPages()
                , reports.stream()
                .map(report -> new GetReportRes(report))
                .toList());
    }

    public DeleteReportRes deleteReport(Long reportId) throws BaseException{
        reportRepository.deleteById(reportId);
        return new DeleteReportRes(reportId);
    }
}
