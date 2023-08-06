package org.rf.rfserver.report.service;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.rf.rfserver.domain.Report;
import org.rf.rfserver.domain.User;
import org.rf.rfserver.report.dto.PostReportReq;
import org.rf.rfserver.report.dto.PostReportRes;
import org.rf.rfserver.report.repository.ReportRepository;
import org.rf.rfserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import static org.rf.rfserver.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public PostReportRes createUserReport(PostReportReq postReportReq) throws BaseException {
        try {
            User reporter = userRepository.findById(postReportReq.getReporterId())
                    .orElseThrow(() -> new BaseException(NO_SUCH_USER));
            User repotedUser = userRepository.findById(postReportReq.getRepotedUserId())
                    .orElseThrow(() -> new BaseException(NO_SUCH_USER));
            Report report = reportRepository.save(new Report(reporter, repotedUser, postReportReq.getContent()));
            return new PostReportRes(report.getId(), report.getReportedUser().getNickName());
        } catch(Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
