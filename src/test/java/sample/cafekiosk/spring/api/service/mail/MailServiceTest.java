package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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

    @Mock
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

        // Mockito.when과 BDDMockito.given은 동일한 동작을 수행하지만, BDDMockito의 given은 BDD의 의미를 부여한 것이다. (그냥 이름만 바뀜)
        Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString())).thenReturn(true);
        BDDMockito.given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString())).willReturn(true);

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        assertThat(result).isTrue();

        // mailSendHistoryRepository가 몇번 돌렸는지(times(1)) 횟수를 검증, 실제 메소드(sendMail)가 호출된 후에 선언해야 한다.
        Mockito.verify(mailSendHistoryRepository, Mockito.times(1)).save(any(MailSendHistory.class));
    }

}