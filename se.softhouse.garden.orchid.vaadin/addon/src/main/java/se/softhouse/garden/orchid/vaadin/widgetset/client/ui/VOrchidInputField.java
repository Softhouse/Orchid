package se.softhouse.garden.orchid.vaadin.widgetset.client.ui;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VTextField;

public class VOrchidInputField extends VTextField {

	public VOrchidInputField() {
		getElement().setAttribute("type", "number");
	}

	/**
	 * Called whenever an update is received from the server
	 */
	@Override
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		if (client.updateComponent(this, uidl, true)) {
			// If client.updateComponent returns true there has been no changes
			// and we
			// do not need to update anything.
			return;
		}

		// Process attributes/variables from the server
		// The attribute names are the same as we used in
		// paintContent on the server-side
		String type = uidl.getStringAttribute("type");
		String pattern = uidl.getStringAttribute("pattern");
		String step = uidl.getStringAttribute("step");

		getElement().setAttribute("type", type);
		if (pattern != null) {
			getElement().setAttribute("pattern", pattern);
		}
		if (step != null) {
			getElement().setAttribute("step", step);

		}
	}

}
