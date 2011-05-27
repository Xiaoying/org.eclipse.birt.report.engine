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

package org.eclipse.birt.report.engine.executor;

import org.eclipse.birt.report.engine.ir.PageVariableDesign;

public class PageVariable
{

	public static final String SCOPE_REPORT = PageVariableDesign.SCOPE_REPORT;
	public static final String SCOPE_PAGE = PageVariableDesign.SCOPE_PAGE;

	protected String scope;
	protected String name;
	protected Object value;
	protected Object defaultValue;

	public PageVariable( String name, String scope )
	{
		this( name, scope, null );
	}

	public PageVariable( String name, String scope, Object value )
	{
		this.name = name;
		this.scope = scope;
		this.value = value;
	}

	public Object getValue( )
	{
		return value;
	}

	public void setValue( Object value )
	{
		this.value = value;
	}

	public String getScope( )
	{
		return scope;
	}

	public String getName( )
	{
		return name;
	}

	public void setDefaultValue( Object value )
	{
		this.defaultValue = value;
	}

	public Object getDefaultValue( )
	{
		return defaultValue;
	}

}
