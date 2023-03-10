package com.spikes.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.spikes.App;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
			GwtApplicationConfiguration config = new GwtApplicationConfiguration(480, 700);
            config.antialiasing = true;
            return config;
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new App();
        }
}