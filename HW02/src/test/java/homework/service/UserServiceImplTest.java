package homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private StreamsIOService streamsIOService;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void welcomeUserNormalWork() {
        given(streamsIOService.readLine()).willReturn("Ololosh");
        assertThat(userService.welcomeUser()).isEqualTo("Ololosh");

    }
}