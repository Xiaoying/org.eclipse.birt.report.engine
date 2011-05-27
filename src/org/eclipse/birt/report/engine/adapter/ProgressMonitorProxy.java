/*******************************************************************************
 * Copyright (c) 2009 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.adapter;

import org.eclipse.birt.report.engine.api.IProgressMonitor;

/**
 * A proxy for IProgressMonitor.
 * 
 *
 */
public class ProgressMonitorProxy implements IProgressMonitor
{
	private IProgressMonitor proxy;

	public ProgressMonitorProxy( IProgressMonitor monitor )
	{
		proxy = monitor;
		initialize( );
	}

	private void initialize( )
	{

	}

	public void onProgress( int type, int page )
	{
		if ( proxy != null )
		{
			proxy.onProgress( type, page );
		}
	}
}
