package com.proj.gui;

import java.awt.Component;

public interface ActiveComponentConstructor<C extends Component, T> {
	C constructActiveListComponent(T listElement);
	C updateActiveListComponent(Component old, T listElement);
}
