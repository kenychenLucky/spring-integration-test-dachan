package com.dachan.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import com.rometools.rome.feed.synd.SyndEntryImpl;

@SpringBootTest({ "auto.startup=false",	  // we don't want to start the real feed
				  "feed.file.name=Test" })   // use a different file
public class FlowTests {

	@Autowired
	private SourcePollingChannelAdapter newsAdapter;

	@Autowired
	private MessageChannel news;

	@Test
	public void test() throws Exception {
		assertThat(this.newsAdapter.isRunning()).isFalse();
		SyndEntryImpl syndEntry = new SyndEntryImpl();
		syndEntry.setTitle("Test ai-keny");
		syndEntry.setLink("https://ai-keny.blog.csdn.net/");
		File out = new File("/tmp/ai_keny/Test");

		out.delete();
		assertThat(out.exists()).isFalse();
		this.news.send(MessageBuilder.withPayload(syndEntry).build());
		assertThat(out.exists()).isTrue();
		BufferedReader br = new BufferedReader(new FileReader(out));
		String line = br.readLine();
		System.out.println("line="+line);
		assertThat(line).isEqualTo("Test ai-keny @ https://ai-keny.blog.csdn.net/");
		br.close();
		out.delete();
	}

}
