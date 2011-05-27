/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

package org.eclipse.birt.report.engine.content.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.util.IOUtil;
import org.eclipse.birt.report.engine.content.IBandContent;
import org.eclipse.birt.report.engine.content.IContent;
import org.eclipse.birt.report.engine.content.IContentVisitor;
import org.eclipse.birt.report.engine.content.IGroupContent;
import org.eclipse.birt.report.engine.content.IReportContent;
import org.eclipse.birt.report.engine.content.IRowContent;
import org.eclipse.birt.report.engine.content.ITableContent;

/**
 * 
 * the row content object which contains cell content objects
 * 
 */
public class RowContent extends AbstractContent implements IRowContent
{
	
	protected int rowID = -1;	
	
	protected String groupId;
	
	RowContent(IRowContent row)
	{
		super(row);
		this.rowID = row.getRowID( );
		this.groupId = row.getGroupId( );
	}

	public int getContentType( )
	{
		return ROW_CONTENT;
	}

	/**
	 * constructor
	 * 
	 * @param row
	 *            the row deign
	 */
	RowContent( IReportContent report )
	{
		super( report );
	}

	public Object accept( IContentVisitor visitor, Object value )
			throws BirtException
	{
		return visitor.visitRow( this, value );
	}

	public int getRowID( )
	{
		return rowID;
	}
	
	public void setRowID(int rowID)
	{
		this.rowID = rowID;
	}
	
	private ITableContent table;
	public ITableContent getTable( )
	{
		if (table != null)
		{
			return table;
		}
		IContent parent = (IContent) getParent( );
		while ( parent != null )
		{
			if ( parent.getContentType( ) == IContent.TABLE_CONTENT )
			{
				table = (ITableContent) parent;
				return table;
			}
			parent = (IContent) parent.getParent( );
		}
		return null;
	}

	static final protected short FIELD_ROWID = 800;
	static final protected short FIELD_ROWTYPE = 801;
	static final protected short FIELD_ROW_GROUPLEVEL = 802;
	static final protected short FIELD_ROW_GROUPID = 803;

	protected void writeFields( DataOutputStream out ) throws IOException
	{
		super.writeFields( out );
		if ( rowID != -1 )
		{
			IOUtil.writeShort( out,  FIELD_ROWID );
			IOUtil.writeInt( out,  rowID );
		}
		if ( groupId != null )
		{
			IOUtil.writeShort( out,  FIELD_ROW_GROUPID );
			IOUtil.writeString( out,  groupId );
		}
	}

	public boolean needSave( )
	{
		return true;
	}

	protected void readField( int version, int filedId, DataInputStream in,
			ClassLoader loader ) throws IOException
	{
		switch ( filedId )
		{
			case FIELD_ROWID :
				rowID = IOUtil.readInt(in);
				break;
			case FIELD_ROWTYPE :
				IOUtil.readInt(in);
				break;
			case FIELD_ROW_GROUPLEVEL :
				IOUtil.readInt(in);
				break;
			case FIELD_ROW_GROUPID :
				groupId = IOUtil.readString( in );
				break;
			default :
				super.readField( version, filedId, in, loader );
		}
	}

	
	/**
	 * @return the groupId
	 */
	public String getGroupId( )
	{
		return groupId;
	}

	
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId( String groupId )
	{
		this.groupId = groupId;
	}

	public IGroupContent getGroup(  )
	{
		IBandContent bandContent = getBand( );
		if ( bandContent != null )
		{
			IContent parent = (IContent) bandContent.getParent();
			if (parent instanceof IGroupContent)
			{
				return (IGroupContent)parent;
			}
		}
		return null;
	}

	public IBandContent getBand( )
	{
		if ( parent instanceof IBandContent )
		{
			return (IBandContent) parent;
		}
		return null;
	}
	
	protected IContent cloneContent()
	{
		return new RowContent(this);
	}
}