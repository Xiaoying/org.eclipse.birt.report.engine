/***********************************************************************
 * Copyright (c) 2009 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Actuate Corporation - initial API and implementation
 ***********************************************************************/

package org.eclipse.birt.report.engine.nLayout.area.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * In run task, the inline text area need to record
 * <ul>
 * <li>floatPos</li>
 * <li>offsetInContent.</li>
 * <li>dimension</li>
 * <li>widthRestrict</li>
 * </ul>
 * to generate page hint.
 * 
 */
public class InlineTextExtension
{
	private ArrayList<InlineTextArea> lines = new ArrayList<InlineTextArea>( );

	private ArrayList<Integer> lineBreaks = new ArrayList<Integer>( );
	
	/**
	 * @see SizeBasedContent#floatPos
	 */
	private int floatPos;
	/**
	 * @see SizeBasedContent#offsetInContent
	 */
	private int offsetInContent;
	/**
	 * @see SizeBasedContent#dimension
	 */
	private int dimension = 0;
	/**
	 * @see SizeBasedContent#width
	 */
	private int widthRestrict;

	/**
	 * when generating page hint, inline text must invoke this method first.
	 */
	public void updatePageHintInfo( InlineTextArea area )
	{
		if ( lineBreaks == null || lineBreaks.size( ) == 0 )
		{
			return;
		}

		offsetInContent = 0;
		floatPos = 0;
		dimension = 0;
		widthRestrict = 0;
		
		int lineNumber = lines.indexOf( area );
		// if current inlineText line is not the first line in current page for
		// the textContent, just ignore it.
		if ( lineNumber != 0 && !lineBreaks.contains( lineNumber - 1 ) )
		{
			return;
		}

		Collections.sort( lineBreaks );
		int startLineNumber = lineNumber;

		for ( int i = 0; i < startLineNumber; i++ )
		{
			offsetInContent += lines.get( i ).getAllocatedWidth( );
		}

		for ( Iterator<Integer> iter = lineBreaks.iterator( ); iter.hasNext( ); )
		{
			int breakLineNumber = iter.next( );
			if ( breakLineNumber >= startLineNumber )
			{
				InlineTextArea startLine = lines.get( startLineNumber );
				floatPos = startLine.getAllocatedX( );
				for ( int i = startLineNumber; i <= breakLineNumber; i++ )
				{
					dimension += lines.get( i ).getAllocatedWidth( );
					widthRestrict = startLine.parent.getWidth( );
				}
				break;
			}
		}
	}

	public void addLine(InlineTextArea area )
	{
		lines.add( area );
	}
	
	public void replaceLine( InlineTextArea oldArea, InlineTextArea newArea )
	{
		int lineNumber = lines.indexOf( oldArea );
		lines.remove( lineNumber );
		lines.add( lineNumber, newArea );
	}
	
	public void addLineBreak( InlineTextArea area )
	{
		int lineNumber = lines.indexOf( area );
		lineBreaks.add( lineNumber );
	}
	
	public void addLineBreak( )
	{
		int lineNumber = lines.size( ) - 1;
		lineBreaks.add( lineNumber );
	}
	
	public int getFloatPos( )
	{
		return floatPos;
	}

	public int getOffsetInContent( )
	{
		return offsetInContent;
	}

	public int getDimension( )
	{
		return dimension;
	}

	public int getWidthRestrict( )
	{
		return widthRestrict;
	}
}
