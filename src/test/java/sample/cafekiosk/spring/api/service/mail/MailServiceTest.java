package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.MailSendHistory;
import sample.cafekiosk.spring.domain.history.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class MailServiceTest {

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
        MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
        MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);

        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        // stubbing
        Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();

        // mailSendHistoryRepository가 몇번 돌렸는지(times(1)) 횟수를 검증, 실제 메소드(sendMail)가 호출된 후에 선언해야 한다.
        Mockito.verify(mailSendHistoryRepository, Mockito.times(1)).save(any(MailSendHistory.class));
    }

}