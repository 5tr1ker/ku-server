package com.team.saver.account.service;

import com.team.saver.account.dto.AccountResponse;
import com.team.saver.account.dto.AccountUpdateRequest;
import com.team.saver.account.dto.MyPageResponse;
import com.team.saver.account.dto.SchoolCertRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.mail.dto.MailSendRequest;
import com.team.saver.mail.service.MailService;
import com.team.saver.quest.dto.MissionLevelResponse;
import com.team.saver.quest.service.MissionService;
import com.team.saver.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final MailService mailService;
    private final S3Service s3Service;
    private final MissionService missionService;

    public Account getProfile(CurrentUser currentUser) {
        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        return account;
    }

    public AccountResponse findAccountDetail(CurrentUser currentUser) {
        Account account = getProfile(currentUser);

        return AccountResponse.of(account);
    }

    public void sendCodeInOrderToCertStudent(CurrentUser currentUser, SchoolCertRequest request) {
        if(!isStudentEmail(request.getSchoolEmail())) {
            throw new CustomRuntimeException(NOT_STUDENT_EMAIL);
        }

        mailService.sendMail(request.getSchoolEmail());
    }

    @Transactional
    public void checkCodeInOrderToCertStudent(CurrentUser currentUser, MailSendRequest request) {
        mailService.checkVerificationCode(request);
        isExistsSchoolEmail(request.getEmail());

        updateRoleToStudent(currentUser, request);
    }

    protected void updateRoleToStudent(CurrentUser currentUser, MailSendRequest request) {
        Account account = getProfile(currentUser);

        updateToleToStudent(account, request.getEmail());
    }

    private void updateToleToStudent(Account account, String schoolEmail) {
        if(isStudentEmail(schoolEmail)) {
            account.updateSchoolEmail(schoolEmail);

            account.updateRoleToStudent();
            return;
        }

        throw new CustomRuntimeException(NOT_STUDENT_EMAIL);
    }

    private void isExistsSchoolEmail(String email) {
        Account account = accountRepository.findBySchoolEmail(email).orElse(null);
        if(account == null) {
            return;
        }

        throw new CustomRuntimeException(EXIST_SCHOOL_EMAIL, account.getEmail(), account.getEmail());
    }

    private boolean isStudentEmail(String email) {
        if(email.endsWith("@kku.ac.kr")) {
            return true;
        }

        return false;
    }

    @Transactional
    public void deleteAccount(CurrentUser currentUser) {
        Account account = getProfile(currentUser);

        account.delete();
    }

    @Transactional
    public void updateAccount(CurrentUser currentUser, AccountUpdateRequest request) {
        Account account = getProfile(currentUser);

        account.update(request);
    }

    @Transactional
    public void updateAccountImage(CurrentUser currentUser, MultipartFile multipartFile) {
        Account account = getProfile(currentUser);

        String profileImage = account.getProfileImage();
        if(profileImage != null) {
            s3Service.deleteImage(profileImage);
        }

        String newProfileImage = s3Service.uploadImage(multipartFile);
        account.setProfileImage(newProfileImage);
    }

    public MyPageResponse getMyPageInfo(CurrentUser currentUser) {
        MissionLevelResponse missionLevelResponse = missionService.getMissionLevelByEmail(currentUser);

        return accountRepository.getMyPageInfo(currentUser.getEmail(), missionLevelResponse.getUserExp(), missionLevelResponse.getUserLevel())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));
    }
}
