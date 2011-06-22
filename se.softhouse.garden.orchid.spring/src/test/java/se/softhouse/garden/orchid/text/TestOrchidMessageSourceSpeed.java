package se.softhouse.garden.orchid.text;

import static se.softhouse.garden.orchid.text.OrchidMessageSource.code;

import java.io.IOException;
import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;

import se.softhouse.garden.orchid.text.TestOrchidMessageSource.TestArguments;
import se.softhouse.garden.orchid.text.TestOrchidMessageSource.TestMessages;

public class TestOrchidMessageSourceSpeed {

	@Test
	public void testOrchidReloadableResourceBundleMessageSource() {
		OrchidReloadableResourceBundleMessageSource ms = new OrchidReloadableResourceBundleMessageSource();
		ms.setBasename("test");
		ms.setUseCodeAsDefaultMessage(true);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 2; i++) {
			Assert.assertEquals("FileTest message 002 with name Micke",
			        ms.getMessage(code(TestMessages.MSG2).arg(TestArguments.NAME, "Micke").arg(TestArguments.ID, 2), Locale.getDefault()));
		}
		long end = System.currentTimeMillis();
		System.out.println("testOrchidReloadableResourceBundleMessageSource");
		System.out.println(end - start);
	}

	@Test
	public void testOrchidDirectoryMessageSource() throws IOException {
		OrchidDirectoryMessageSource ms = new OrchidDirectoryMessageSource();
		ms.setRoot("texttest/test");
		ms.setCacheSeconds(0);
		ms.setUseCodeAsDefaultMessage(true);
		ms.start();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 2; i++) {
			Assert.assertEquals("FileTest message 002 with name Micke",
			        ms.getMessage(code(TestMessages.MSG2).arg(TestArguments.NAME, "Micke").arg(TestArguments.ID, 2), Locale.getDefault()));
		}
		long end = System.currentTimeMillis();
		System.out.println("testOrchidDirectoryMessageSource");
		System.out.println(end - start);
	}

}