package be.thibaulthelsmoortel.discordbotbase;

import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
abstract class BaseTest {

    @SuppressWarnings("unused") // Currently mocking this class is enough (provides mock token)
    @MockBean
    private DiscordBotEnvironment discordBotEnvironment;

}
