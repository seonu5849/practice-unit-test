package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.MailSendHistory;
import sample.cafekiosk.spring.domain.history.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

// 테스트가 시작될 때, Mockito를 이용해서 mock을 만들거라는 것을 인지시키기 위해 사용
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Spy
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    // MailService 객체를 생성시 필요한 인자들을 mock으로 넣어주기 위한 어노테이션
    // Bean으로 자동 주입하는 DI와 비슷.
    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given

        // stubbing
//        Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
//                .thenReturn(true);

        // @Spy는 실제 객체의 메서드를 감시(Spying)합니다.
        // standard stubbing(when().thenReturn())은 대상 메서드를 실제로 한 번 호출하기 때문에,
        // 실제 메일 발송 같은 부수 효과(Side Effect)를 차단하고자 doReturn() 방식을 적용합니다.
        Mockito.doReturn(true)
                .when(mailSendClient)
                .sendEmail(anyString(), anyString(), anyString(), anyString());

        // @Spy는 실제 객체를 기반으로 생성되므로, 스터빙(Stubbing)하지 않은 메서드는 실제 로직을 수행합니다.
        // 아래 로그는 MailSendClient의 a(), b(), c() 메서드가 실제 구현체대로 실행되었음을 보여줍니다.
        /**
         * 16:48:27.235 [Test worker] INFO sample.cafekiosk.spring.client.mail.MailSendClient -- a
         * 16:48:27.237 [Test worker] INFO sample.cafekiosk.spring.client.mail.MailSendClient -- b
         * 16:48:27.237 [Test worker] INFO sample.cafekiosk.spring.client.mail.MailSendClient -- c
         */

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();

        // mailSendHistoryRepository가 몇번 돌렸는지(times(1)) 횟수를 검증, 실제 메소드(sendMail)가 호출된 후에 선언해야 한다.
        Mockito.verify(mailSendHistoryRepository, Mockito.times(1)).save(any(MailSendHistory.class));
    }

}