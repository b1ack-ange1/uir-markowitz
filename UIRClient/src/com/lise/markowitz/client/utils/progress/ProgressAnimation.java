package com.lise.markowitz.client.utils.progress;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.widgets.Progressbar;

public class ProgressAnimation extends Timer {
	private Progressbar progressbar;

	public ProgressAnimation(Progressbar progressbar) {
		this.progressbar = progressbar;
	}

	public void run() {
		if (progressbar.getPercentDone() != 100)
			progressbar.setPercentDone(progressbar.getPercentDone() + 10);
		else
			progressbar.setPercentDone(0);
	}
}
