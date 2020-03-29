package com.verminsnest.exceptions;

import com.verminsnest.misc.Button;

public class OutOfBounds extends Exception {
	private static final long serialVersionUID = 1L;

	public OutOfBounds(Button button){
		super("The size of button: "+button.getText()+" exceeds the bounds.");
	}
	public OutOfBounds(String str){
		super("The size of text: "+ str+ " exceeds the bounds.");
	}
}
