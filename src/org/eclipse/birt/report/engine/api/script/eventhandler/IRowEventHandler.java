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
import org.eclipse.birt.report.engine.api.script.element.IRow;
import org.eclipse.birt.report.engine.api.script.instance.IRowInstance;

/**
 * Script event handler interface for a detail row
 */
public interface IRowEventHandler
{
	/**
	 * Handle the onPrepare event
	 */
	void onPrepare( IRow rowHandle, IReportContext reportContext )
			throws ScriptException;

	/**
	 * Handle the onCreate event
	 */
	void onCreate( IRowInstance rowInstance, IReportContext reportContext )
			throws ScriptException;

	/**
	 * Handle the onRender event
	 */
	void onRender( IRowInstance rowInstance, IReportContext reportContext )
			throws ScriptException;

	/**
	 * Handle the onPageBreak event
	 */
	void onPageBreak( IRowInstance rowInstance, IReportContext reportContext )
			throws ScriptException;

}
