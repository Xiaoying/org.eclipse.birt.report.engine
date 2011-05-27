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
import org.eclipse.birt.report.engine.api.script.element.IDataItem;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

/**
 * Script event handler interface for a data item
 */
public interface IDataItemEventHandler
{

	/**
	 * Handle the onPrepare event
	 */
	void onPrepare( IDataItem dataItemHandle, IReportContext reportContext )
			throws ScriptException;

	/**
	 * Handle the onCreate event
	 */
	void onCreate( IDataItemInstance data, IReportContext reportContext )
			throws ScriptException;

	/**
	 * Handle the onRender event
	 */
	void onRender( IDataItemInstance data, IReportContext reportContext )
			throws ScriptException;
	
	/**
	 * Handle the onPageBreak event
	 */
	void onPageBreak( IDataItemInstance data, IReportContext reportContext )
			throws ScriptException;
}
