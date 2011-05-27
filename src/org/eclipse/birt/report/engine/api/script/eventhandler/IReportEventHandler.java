/*******************************************************************************
 * Copyright (c) 2005 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.api.script.eventhandler;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.IReportDesign;

/**
 * Script event handler interface for a report
 */
public interface IReportEventHandler
{
	/**
	 * Handle the initialize event
	 */
	void initialize( IReportContext reportContext ) throws ScriptException;

	/**
	 * Handle the beforeFactory event
	 */
	void beforeFactory( IReportDesign report, IReportContext reportContext )
			throws ScriptException;

	/**
	 * Handle the afterFactory event
	 */
	void afterFactory( IReportContext reportContext ) throws ScriptException;

	/**
	 * Handle the beforeRender event
	 */
	void beforeRender( IReportContext reportContext ) throws ScriptException;

	/**
	 * Handle the afterRender event
	 */
	void afterRender( IReportContext reportContext ) throws ScriptException;
	
	/**
	 * Handle the onPrepare event
	 */
	void onPrepare( IReportContext reportContext ) throws ScriptException;
}
