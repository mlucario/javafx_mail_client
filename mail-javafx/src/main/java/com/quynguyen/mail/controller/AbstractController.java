package com.quynguyen.mail.controller;

import com.quynguyen.mail.model.ModelAccess;

public abstract class AbstractController {

	private ModelAccess modelAccess;

	public AbstractController(ModelAccess modelAccess) {
		this.modelAccess = modelAccess;
	}
	
	public ModelAccess getModelAccess() {
		return this.modelAccess;
	}
}
