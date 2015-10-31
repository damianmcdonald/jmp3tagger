package com.damianmcdonald.github.jmp3tagger.support;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JMP3TaggerBannerProvider implements BannerProvider {

	public String name() { return "jmp3tagger"; }

	public String getBanner() {
		return "\r\n\r\n     __              ________  __                                      \r\n    |__| _____ ______\\_____  \\/  |______     ____   ____   ___________ \r\n    |  |/     \\\\____ \\ _(__  <   __\\__  \\   / ___\\ / ___\\_/ __ \\_  __ \\\r\n    |  |  Y Y  \\  |_> >       \\  |  / __ \\_/ /_/  > /_/  >  ___/|  | \\/\r\n/\\__|  |__|_|  /   __/______  /__| (____  /\\___  /\\___  / \\___  >__|   \r\n\\______|     \\/|__|         \\/          \\//_____//_____/      \\/       \r\n\r\n";
	}

	public String getVersion() { return "jmp3tagger 1.0.0";	}

	public String getWelcomeMessage() { return "Welcome to jmp3tagger";	}
	
	public String getProviderName() { return "Spring Shell 1.1.0-RELEASE"; }

}
