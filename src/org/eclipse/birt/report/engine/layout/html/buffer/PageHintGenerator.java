/***********************************************************************
 * Copyright (c) 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.report.engine.layout.html.buffer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.birt.report.engine.content.IContent;

public class PageHintGenerator
{

	protected IContent startContent = null;
	protected IContent currentContent = null;
	protected ArrayList pageHint = new ArrayList( );

	public void start( IContent content, boolean isFirst )
	{
		if ( startContent == null )
		{
			if ( isFirst )
			{
				startContent = content;
				currentContent = content;
			}
		}
		else
		{
			if ( !isFirst )
			{
				if ( currentContent != null )
				{
					pageHint.add( new IContent[]{startContent, currentContent} );
					startContent = null;
					currentContent = null;
				}
			}
			else
			{
				currentContent = content;
			}
		}
	}

	public void end( IContent content, boolean finished )
	{
		if ( !finished )
		{
			if ( currentContent != null )
			{
				pageHint.add( new IContent[]{startContent, currentContent} );
				startContent = null;
				currentContent = null;
			}
		}
	}

	public void reset( )
	{
		startContent = null;
		currentContent = null;
		pageHint.clear( );
	}

	public List getPageHint( )
	{
		ArrayList hint = new ArrayList( );
		hint.addAll( pageHint );
		if ( startContent != null )
		{
			if ( currentContent != null )
			{
				hint.add( new IContent[]{startContent, currentContent} );
			}
		}
		return hint;
	}

}
