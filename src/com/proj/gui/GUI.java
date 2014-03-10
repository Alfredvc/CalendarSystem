package com.proj.gui;

import java.awt.Composite;
import javax.annotation.PostConstruct;

public class GUI {
	@PostConstruct
	public void createControls(Composite parent){
		System.out.println("createControls method called");
	}
}