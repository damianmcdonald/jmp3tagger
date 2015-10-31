package com.damianmcdonald.github.jmp3tagger.support;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JMP3TaggerPromptProvider implements PromptProvider {

	public String getPrompt() { return "jmp3tagger>"; }

	public String getProviderName() { return "default prompt provider"; }

	public String name() { return "jmp3tagger"; }

}
