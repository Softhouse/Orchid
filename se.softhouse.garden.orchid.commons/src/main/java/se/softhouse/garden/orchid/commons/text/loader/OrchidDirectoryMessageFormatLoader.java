/**
 * Copyright (c) 2011, Mikael Svahn, Softhouse Consulting AB
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so:
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package se.softhouse.garden.orchid.commons.text.loader;

import java.util.Locale;

import se.softhouse.garden.orchid.commons.text.OrchidMessageArguments;
import se.softhouse.garden.orchid.commons.text.OrchidMessageFormat;
import se.softhouse.garden.orchid.commons.text.OrchidMessageFormat.OrchidMessageFormatFunctionResolver;
import se.softhouse.garden.orchid.commons.text.OrchidMessageFormatFunction;
import se.softhouse.garden.orchid.commons.text.loader.OrchidDirectoryMessageCache.MessageFactory;

/**
 * A message loader that load OrchidMessageFormats from bare files in a
 * directory structure.
 * 
 * @author Mikael Svahn
 * 
 */
public class OrchidDirectoryMessageFormatLoader extends OrchidDirectoryMessageLoader<OrchidMessageFormat> {

	private final MessageFactory<OrchidMessageFormat> messageFactory = new MessageFactory<OrchidMessageFormat>() {

		@Override
		public OrchidMessageFormat createMessage(String message, Locale locale) {
			OrchidMessageFormat messageFormat = new OrchidMessageFormat("", locale);
			messageFormat.setFunctionResolver(resolveFunction());
			messageFormat.applyPattern(message);
			return messageFormat;
		}

	};

	/**
	 * The constructor which creates an empty cache
	 */
	public OrchidDirectoryMessageFormatLoader() {
		super();
	}

	/**
	 * Creates an instance and loads the content from the specified root path.
	 * 
	 * @param root
	 *            The relative path
	 */
	public OrchidDirectoryMessageFormatLoader(String root) {
		super(root);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * se.softhouse.garden.orchid.commons.text.loader.OrchidDirectoryMessageLoader
	 * #createMessage()
	 */
	@Override
	protected MessageFactory<OrchidMessageFormat> createMessage() {
		return this.messageFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * se.softhouse.garden.orchid.commons.text.loader.OrchidDirectoryMessageLoader
	 * #getMessageFromCache(java.lang.String, java.util.Locale)
	 */
	@Override
	protected OrchidMessageFormat getMessageFromCache(String code, Locale locale) {
		OrchidMessageFormat message = super.getMessageFromCache(code, locale);
		if (message != null) {
			message = (OrchidMessageFormat) message.clone();
			message.setLocale(locale);
		}
		return message;
	}

	/**
	 * Returns a "function" for resolving functions in a Message
	 * 
	 * @return The function resolver
	 */
	private OrchidMessageFormatFunctionResolver resolveFunction() {
		return new OrchidMessageFormatFunctionResolver() {
			@Override
			protected Object resolveFunction(String function, String value) {
				return new OrchidMessageFormatFunction(function, value) {
					@Override
					public Object execute(OrchidMessageArguments args, Locale locale) {
						if ("m".equals(this.function)) {
							OrchidMessageFormat message = getMessage(this.value, locale);
							if (message != null) {
								return message.format(args);
							}
							return "{" + this.value + "}";
						}
						return super.execute(args, locale);
					}
				};
			}
		};
	}
}